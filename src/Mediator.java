public class Mediator {


    public static void main(String args[]){

        RestClient restClient = new RestClient();
        JdbcClient jdbcClient = new JdbcClient();
        System.out.println(jdbcClient.getFilmsByTitle("Manito"));

        System.out.println(restClient.getDescriptionByTitle("Manito"));

    }
}
