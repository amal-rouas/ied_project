import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SparqlClient {
    public HashMap<String, String> getDescriptionByTitle(String title) {
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "SELECT ?filmName ?redactorName ?actorName ?producerName WHERE {\n" +
                "                ?f a dbo:Film ;\n" +
                "                dbp:name \"%s\"@en ;\n" +
                "                dbp:name ?filmName ;\n" +
                "                dbo:director ?r ;\n" +
                "                dbo:starring ?a;\n" +
                "                dbo:producer ?p.\n" +
                "      ?r rdfs:label ?redactorName.\n" +
                "      ?a rdfs:label ?actorName.\n" +
                "      ?p rdfs:label ?producerName.\n" +
                "      FILTER (lang(?redactorName) = 'en').\n" +
                "      FILTER (lang(?actorName) = 'en').\n" +
                "      FILTER (lang(?producerName) = 'en').\n" +
                "}";

        QueryExecutionHTTP qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql"
                , String.format(queryString, title));
        ResultSet resultSet = qexec.execSelect();

        HashMap<String, String> dict = new HashMap<String, String>();
        Set<String> redactors = new HashSet<String>();
        Set<String> actors = new HashSet<String>(), producers = new HashSet<String>();
        for (; resultSet.hasNext(); ) {
            QuerySolution soln = resultSet.nextSolution();
            // Get a result variable by name.
            redactors.add(soln.get("redactorName").toString().split("@")[0]);
            actors.add(soln.get("actorName").toString().split("@")[0]);
            producers.add(soln.get("producerName").toString().split("@")[0]);

        }

        dict.put("redactorName", redactors.toString());
        dict.put("actorName", actors.toString());
        dict.put("producerName", producers.toString());

        return dict;
    }

    public HashMap<String, HashMap<String, Set<String>>> getDescriptionByActor(String actor) {
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "SELECT ?filmName ?redactorName ?producerName WHERE {\n" +
                "                ?f a dbo:Film ;\n" +
                "                   rdfs:label ?filmName ;\n" +
                "                   dbo:director ?r ;\n" +
                "                   dbo:starring ?a;\n" +
                "                   dbo:producer ?p.\n" +
                "                ?a rdfs:label \"%s\"@en .\n" +
                "                ?r  rdfs:label ?redactorName.\n" +
                "                ?p rdfs:label ?producerName.\n" +
                "                 FILTER (lang(?redactorName) = 'en')\n" +
                "                 FILTER (lang(?filmName) = 'en')\n" +
                "                 FILTER (lang(?producerName) = 'en')\n" +
                "                }\n" +
                "ORDER BY ASC(?filmName)";
        QueryExecutionHTTP qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql"
                , String.format(queryString, actor));
        ResultSet resultSet = qexec.execSelect();
        HashMap<String, HashMap<String, Set<String>>> Filmsdict = new HashMap<String, HashMap<String, Set<String>>>();
        HashMap<String, Set<String>> dict;
        Set<String> redactors, actors, producers;
        String filmTitle, producerName, redactorName;

        for (; resultSet.hasNext(); ) {
            redactors = new HashSet<String>();
            actors = new HashSet<String>();
            producers = new HashSet<String>();
            dict = new HashMap<String, Set<String>>();
            QuerySolution soln = resultSet.nextSolution();

            filmTitle = soln.get("filmName").toString().split("@")[0];
            redactorName = soln.get("redactorName").toString().split("@")[0];
            producerName = soln.get("producerName").toString().split("@")[0];

            if (Filmsdict.containsKey(filmTitle)) {
                Filmsdict.get(filmTitle).get("redactorName").add(redactorName);
                Filmsdict.get(filmTitle).get("producerName").add(producerName);
            } else {
                redactors.add(redactorName);
                producers.add(producerName);
                dict.put("redactorName", redactors);
                dict.put("producerName", producers);
                Filmsdict.put(filmTitle, dict);
            }

        }
        return Filmsdict;
    }
    /*public ResultSet queryExecSparql(String queryString, String title){
        QueryExecutionHTTP qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql"
                , String.format(queryString, title));
        ResultSet resultSet = qexec.execSelect();
        return resultSet;
    }
    public HashMap<String, String> resultQueryExecSparql(String queryString, String title){
        ResultSet resultSet = queryExecSparql(queryString, title);
        HashMap<String, String> dict = new HashMap<String, String>();
        Set<String> redactors = new HashSet<String>();
        Set<String> actors = new HashSet<String>(), producers = new HashSet<String>();
        for (; resultSet.hasNext(); ) {
            QuerySolution soln = resultSet.nextSolution();
            // Get a result variable by name.
            redactors.add(soln.get("redactorName").toString().split("@")[0]);
            actors.add(soln.get("actorName").toString().split("@")[0]);
            producers.add(soln.get("producerName").toString().split("@")[0]);

        }

        dict.put("redactorName", redactors.toString());
        dict.put("actorName", actors.toString());
        dict.put("producerName", producers.toString());

        return dict;
    }*/

}