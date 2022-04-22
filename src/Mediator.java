public class Mediator {


    public static void main(String args[]){

        RestClient restClient = new RestClient();

        System.out.println(restClient.getDescriptionByTitle("Manito"));

    }
}
