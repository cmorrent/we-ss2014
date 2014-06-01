package data;

import at.ac.tuwien.big.we14.lab4.dbpedia.api.*;
import at.ac.tuwien.big.we14.lab4.dbpedia.vocabulary.*;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.math.BigDecimal;

import play.db.jpa.Transactional;
import models.Category;
import models.Choice;
import models.Question;
import models.QuizDAO;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.impl.PrefixMappingImpl;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

import play.Logger;
//import org.slf4j.*;

public class DBPediaQuestionInserter {

	public static void insertDBPediaQuestions() {
		// Check if DBpedia is available 
		if(!DBPediaService.isAvailable()) { 
			Logger.warn("DBpedia is currently not available.");
			return;
		} else {
			Logger.info("DBpedia is available.");
		}
		
		generateAndInsertQuestions();
		Logger.info("Inserted questions from DBPedia.");

	}

	@Transactional
	private static void generateAndInsertQuestions() {

		// Generate new Category 'Allerlei' ('Miscellaneous')
		Category category_misc = new Category();
		category_misc.setNameDE("Allerlei");
		category_misc.setNameEN("Miscellaneous");

		// Question 1:
		Resource director = DBPediaService.loadStatements(DBPedia.createResource("Tim_Burton")); 

		String englishDirectorName = DBPediaService.getResourceName(director, Locale.ENGLISH); 
		String germanDirectorName = DBPediaService.getResourceName(director, Locale.GERMAN); 

		// build SPARQL-query 
		SelectQueryBuilder movieQuery = DBPediaService.createQueryBuilder() 
		 .setLimit(3) // at most five statements 
		 .addWhereClause(RDF.type, DBPediaOWL.Film) 
		 .addPredicateExistsClause(FOAF.name) 
		 .addWhereClause(DBPediaOWL.director, director) 
		 .addFilterClause(RDFS.label, Locale.GERMAN) 
		 .addFilterClause(RDFS.label, Locale.ENGLISH);

		Logger.info("query: "+movieQuery.toQueryString());

		// retrieve data from dbpedia 
		Model timBurtonMovies = DBPediaService.loadStatements(movieQuery.toQueryString()); 
		// get english and german movie names, e.g., for right choices 
		List<String> englishTimBurtonMovieNames = 
		 DBPediaService.getResourceNames(timBurtonMovies, Locale.ENGLISH); 
		List<String> germanTimBurtonMovieNames = 
		 DBPediaService.getResourceNames(timBurtonMovies, Locale.GERMAN); 
		// alter query to get movies without tim burton 
		movieQuery.removeWhereClause(DBPediaOWL.director, director); 
		movieQuery.addMinusClause(DBPediaOWL.director, director); 
		// retrieve data from dbpedia 
		Model noTimBurtonMovies = DBPediaService.loadStatements(movieQuery.toQueryString()); 
		// get english and german movie names, e.g., for wrong choices 
		List<String> englishNoTimBurtonMovieNames = 
		 DBPediaService.getResourceNames(noTimBurtonMovies, Locale.ENGLISH); 
		List<String> germanNoTimBurtonMovieNames = 
		 DBPediaService.getResourceNames(noTimBurtonMovies, Locale.GERMAN);

		Logger.info("germanTimBurtonMovieNames: "+germanTimBurtonMovieNames);
		Logger.info("germanNoTimBurtonMovieNames: "+germanNoTimBurtonMovieNames);

		// Create Question:
		Question q1 = new Question();
		String q1textDE = "Bei welchem Film führte "+germanDirectorName+" Regie?";
		String q1textEN = "Which film was directed by "+englishDirectorName+"?";
		q1.setTextDE(q1textDE);
		q1.setTextEN(q1textEN);
		q1.setMaxTime(new BigDecimal(30));

		for (int i = 0; i< englishNoTimBurtonMovieNames.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(englishNoTimBurtonMovieNames.get(i));
			c.setTextDE(germanNoTimBurtonMovieNames.get(i));
			q1.addWrongChoice(c);
		}

		for (int i = 0; i< englishTimBurtonMovieNames.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(englishTimBurtonMovieNames.get(i));
			c.setTextDE(germanTimBurtonMovieNames.get(i));
			q1.addRightChoice(c);
		}

		category_misc.addQuestion(q1);

		QuizDAO.INSTANCE.persist(category_misc);

	}

}