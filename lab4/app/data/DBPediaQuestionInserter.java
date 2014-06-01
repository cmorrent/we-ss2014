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

		// Question 2:
		SelectQueryBuilder qb2 = DBPediaService.createQueryBuilder() 
		 .setLimit(4) // at most five statements 
		 .addWhereClause(DCTerms.subject,DBPedia.createProperty("Category:Countries_in_Europe"))
		 .setOffset(12);

		Model q2m = DBPediaService.loadStatements(qb2.toQueryString());
		List<String> q2strDE = DBPediaService.getResourceNames(q2m, Locale.GERMAN);
		List<String> q2strEN = DBPediaService.getResourceNames(q2m, Locale.ENGLISH);

		qb2.removeWhereClause(DCTerms.subject, DBPedia.createProperty("Category:Countries_in_Europe"));
		qb2.addWhereClause(DCTerms.subject, DBPedia.createProperty("Category:Countries_in_Africa"));

		Model q21 = DBPediaService.loadStatements(qb2.toQueryString());
		List<String> q21DE = DBPediaService.getResourceNames(q21, Locale.GERMAN);
		List<String> q21EN = DBPediaService.getResourceNames(q21, Locale.ENGLISH);

		// Create Question:
		Question q2 = new Question();
		String q2textDE = "Welche Länder liegen in Europa?";
		String q2textEN = "Which countries are in Europe?";
		q2.setTextDE(q2textDE);
		q2.setTextEN(q2textEN);
		q2.setMaxTime(new BigDecimal(30));

		for (int i = 0; i< q2strEN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q2strEN.get(i));
			c.setTextDE(q2strDE.get(i));
			q2.addRightChoice(c);
		}

		for (int i = 0; i< q21EN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q21EN.get(i));
			c.setTextDE(q21DE.get(i));
			q2.addWrongChoice(c);
		}

		category_misc.addQuestion(q2);

		// Question 3:
		SelectQueryBuilder qb3 = DBPediaService.createQueryBuilder() 
		 .setLimit(4) // at most five statements 
		 .addWhereClause(DBPProp.createResource("order"),DBPedia.createProperty("List_of_mayors_of_Vienna"));

		Model q3m = DBPediaService.loadStatements(qb3.toQueryString());
		List<String> q3strDE = DBPediaService.getResourceNames(q3m, Locale.GERMAN);
		List<String> q3strEN = DBPediaService.getResourceNames(q3m, Locale.ENGLISH);

		qb3.removeWhereClause(DBPProp.createResource("order"),DBPedia.createProperty("List_of_mayors_of_Vienna"));
		qb3.addWhereClause(DBPProp.createResource("order"),DBPedia.createProperty("Governing_Mayor_of_Berlin"));	

		Model q31 = DBPediaService.loadStatements(qb3.toQueryString());
		List<String> q31DE = DBPediaService.getResourceNames(q31, Locale.GERMAN);
		List<String> q31EN = DBPediaService.getResourceNames(q31, Locale.ENGLISH);

		// Create Question:
		Question q3 = new Question();
		String q3textDE = "Wer war Bürgermeister von Wien?";
		String q3textEN = "Who was Major of Vienna?";
		q3.setTextDE(q3textDE);
		q3.setTextEN(q3textEN);
		q3.setMaxTime(new BigDecimal(30));

		for (int i = 0; i< q3strEN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q3strEN.get(i));
			c.setTextDE(q3strDE.get(i));
			q3.addRightChoice(c);
		}

		for (int i = 0; i< q31EN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q31EN.get(i));
			c.setTextDE(q31DE.get(i));
			q3.addWrongChoice(c);
		}

		category_misc.addQuestion(q3);		

		// Question 4:
		SelectQueryBuilder qb4 = DBPediaService.createQueryBuilder() 
		 .setLimit(4) // at most five statements 
		 .addWhereClause(DCTerms.subject,DBPedia.createProperty("Category:Presidents_of_the_United_States"));

		Model q4m = DBPediaService.loadStatements(qb4.toQueryString());
		List<String> q4strDE = DBPediaService.getResourceNames(q4m, Locale.GERMAN);
		List<String> q4strEN = DBPediaService.getResourceNames(q4m, Locale.ENGLISH);

		qb4.removeWhereClause(DCTerms.subject,DBPedia.createProperty("Category:Presidents_of_the_United_States"));
		qb4.addWhereClause(DCTerms.subject,DBPedia.createProperty("Category:Presidents_of_Austria"));	

		Model q41 = DBPediaService.loadStatements(qb4.toQueryString());
		List<String> q41DE = DBPediaService.getResourceNames(q41, Locale.GERMAN);
		List<String> q41EN = DBPediaService.getResourceNames(q41, Locale.ENGLISH);

		// Create Question:
		Question q4 = new Question();
		String q4textDE = "Wer war Präsident der USA?";
		String q4textEN = "Who was President of the United States of America?";
		q4.setTextDE(q4textDE);
		q4.setTextEN(q4textEN);
		q4.setMaxTime(new BigDecimal(30));

		for (int i = 0; i< q4strEN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q4strEN.get(i));
			c.setTextDE(q4strDE.get(i));
			q4.addRightChoice(c);
		}

		for (int i = 0; i< q41EN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q41EN.get(i));
			c.setTextDE(q41DE.get(i));
			q4.addWrongChoice(c);
		}

		category_misc.addQuestion(q4);

		// Question 5:
		SelectQueryBuilder qb5 = DBPediaService.createQueryBuilder() 
		 .setLimit(4) // at most five statements 
		 .addWhereClause(DBPediaOWL.locatedInArea,DBPedia.createProperty("Austria"))
		 .setOffset(8);

		Model q5m = DBPediaService.loadStatements(qb5.toQueryString());
		List<String> q5strDE = DBPediaService.getResourceNames(q5m, Locale.GERMAN);
		List<String> q5strEN = DBPediaService.getResourceNames(q5m, Locale.ENGLISH);

		qb5.removeWhereClause(DBPediaOWL.locatedInArea,DBPedia.createProperty("Austria"));
		qb5.addWhereClause(DBPediaOWL.locatedInArea,DBPedia.createProperty("United_States"));	

		Model q51 = DBPediaService.loadStatements(qb5.toQueryString());
		List<String> q51DE = DBPediaService.getResourceNames(q51, Locale.GERMAN);
		List<String> q51EN = DBPediaService.getResourceNames(q51, Locale.ENGLISH);

		// Create Question:
		Question q5 = new Question();
		String q5textDE = "Welche Berge liegen in Österreich?";
		String q5textEN = "Which mountains are in Austria?";
		q5.setTextDE(q5textDE);
		q5.setTextEN(q5textEN);
		q5.setMaxTime(new BigDecimal(30));

		for (int i = 0; i< q5strEN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q5strEN.get(i));
			c.setTextDE(q5strDE.get(i));
			q5.addRightChoice(c);
		}

		for (int i = 0; i< q51EN.size(); i++) {
			Choice c = new Choice();
			c.setTextEN(q51EN.get(i));
			c.setTextDE(q51DE.get(i));
			q5.addWrongChoice(c);
		}

		category_misc.addQuestion(q5);		

		QuizDAO.INSTANCE.persist(category_misc);

	}

}