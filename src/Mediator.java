import java.io.IOException;

public class Mediator {


    public static void main(String args[]) throws Exception {

        RestClient restClient = new RestClient();
        JdbcClient jdbcClient = new JdbcClient();
        SparqlClient sparqlClient = new SparqlClient();
        //System.out.println(jdbcClient.getFilmsByTitle("Manito"));
        //System.out.println(restClient.getDescriptionByTitle("Manito"));
        //System.out.println(sparqlClient.getDescriptionByTitle("Manito"));
        System.out.println(sparqlClient.getDescriptionByActor("Fulanito"));
    }
}
