import java.io.IOException;

public class Mediator {


    public static void main(String args[]) throws Exception {

        RestClient restClient = new RestClient();
        JdbcClient jdbcClient = new JdbcClient();
        SparqlClient sparqlClient = new SparqlClient();
        //System.out.println(jdbcClient.getFilmsByTitle("Pirates of the Caribbean"));
        //System.out.println(restClient.getDescriptionByTitle("Pirates of the Caribbean"));
        //System.out.println(sparqlClient.getDescriptionByTitle("Pirates of the Caribbean"));
        System.out.println(sparqlClient.getDescriptionByActor("Kevin McNally"));
    }
}
