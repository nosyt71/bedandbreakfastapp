package vttp2023.batch4.paf.assessment.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {
	
	// You may add additional dependency injections
	

	@Autowired
	private MongoTemplate template;

	/*
	db.listings.distinct("address.suburb", { "address.suburb" : { $nin: ["", null]}});
	 */
	public List<String> getSuburbs(String country) {
		return template.findDistinct(Query.query(Criteria.where("address.suburb")
								.nin(null,"")), "address.suburb", "listings", String.class);
	}

	/*
	db.listings.aggregate([
	{
		$match: {
			"$and":[
			{"address.suburb": {$regex: <suburb>, $options: "i"}},
			{price: { $lte : <price_range> },
			accommodates: { $gte: <accommodates>},
			min_nights: {$lte: <duration>}}
			] }
	},
	{
		$project: { _id: 1 , name: 1, accommodates: 1, price: 1}
	},
	{
		$sort: { price: -1}
	}
	]);
	 */
	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {
		MatchOperation matchSuburb = Aggregation.match(
			Criteria.where("address.suburb")
					.regex(suburb, "i")
					.and("price")
					.lte(priceRange)
					.and("accommodates")
					.gte(persons)
					.and("min_nights")
					.lte(duration)
					);
		
		ProjectionOperation projectListings = Aggregation.project("_id", "name", "accommodates", "price");

		SortOperation sortByPrice = Aggregation.sort(Sort.by(Direction.DESC, "price"));

		Aggregation pipeline = Aggregation.newAggregation(matchSuburb, projectListings, sortByPrice);

		AggregationResults<AccommodationSummary> results = template.aggregate(pipeline, "listings", AccommodationSummary.class);

		return results.getMappedResults();
	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
