package vttp2023.batch4.paf.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp2023.batch4.paf.assessment.repositories.ListingsRepository;
import vttp2023.batch4.paf.assessment.services.ForexService;
import vttp2023.batch4.paf.assessment.services.ListingsService;

@SpringBootApplication
public class AssessmentApplication implements CommandLineRunner{

	@Autowired
	private ForexService fo;

	@Autowired
	private ListingsService listingsSvc;

	@Autowired
	private ListingsRepository listingsRepo;

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--------------");
		// System.out.println(listingsRepo.getSuburbs("Australia"));
		// System.out.println(listingsRepo.findListings("Alexandria", 2, 10, 1000));
		// System.out.println(fo.convert("AUD", "SGD", 10));
	}

}
