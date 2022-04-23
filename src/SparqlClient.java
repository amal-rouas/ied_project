import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

import java.util.LinkedList;
import java.util.List;

public class SparqlClient {
    public List<String> getDescriptionByTitle(String title) {
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "SELECT ?f  ?r ?a ?p WHERE {\n" +
                "?f a dbo:Film ;\n" +
                "   foaf:name \"%s\"@en ;\n" +
                "   dbo:director ?r ;\n" +
                "   dbo:starring ?a;\n" +
                "  dbo:producer ?p.\n" +
                " }";
        return queryExecSparql(queryString, title);
    }

    public List<String> getDescriptionByActor(String actor) {
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "SELECT ?f ?r ?a ?p WHERE {\n" +
                "?f a dbo:Film ;\n" +
                "   dbo:director ?r ;\n" +
                "   dbo:starring ?a;\n" +
                "   dbo:producer ?p.\n" +
                "?a foaf:name \"%s\"@en .\n" +
                "}";
        return queryExecSparql(queryString, actor);
    }
    public List<String> queryExecSparql(String queryString, String title){
        RDFNode x = null;
        Resource r = null;
        Literal l = null;
        List<String> list = new LinkedList<>();

        QueryExecutionHTTP qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql"
                , String.format(queryString, title));

        ResultSet results = qexec.execSelect();
        for (; results.hasNext(); ) {
            QuerySolution soln = results.nextSolution();
            // Get a result variable by name.
            list.add(soln.get("f").toString());
            list.add(soln.get("r").toString());
            list.add(soln.get("a").toString());
            list.add(soln.get("p").toString());
            list.add("\n");

            r = soln.getResource("VarR"); // Get a result variable - must be a resource
            l = soln.getLiteral("VarL");   // Get a result variable - must be a literal
        }

        return list;
    }

}