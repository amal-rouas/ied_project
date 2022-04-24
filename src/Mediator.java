import java.io.IOException;

public class Mediator {


    public static void main(String args[]) throws Exception {

        RestClient restClient = new RestClient();

        JdbcClient jdbcClient = new JdbcClient();

        SparqlClient sparqlClient = new SparqlClient();
        sparqlClient.getFilmByTitle("Pirates of the Caribbean");
        sparqlClient.getFilmsByActor("Kevin McNally");
    }
}
