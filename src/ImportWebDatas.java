import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ImportWebDatas {
    public static final List<String> gender_list =  Arrays.asList("Adventure", "Comedy", "Drama", "Action", "Thriller-or-Suspense","Romantic-Comedy");
    public static String url = "https://www.the-numbers.com/market/%s/genre/%s";


    public static File directory = createDirectory();

    public static File createDirectory(){
        File theDir = new File("./WebDatas");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        return theDir;
    }

    public static void main(String[] args) throws IOException {
        for(String gender: gender_list){
            StringBuilder sb = new StringBuilder();
            sb.append("Rank\tMovie\tRelease Date\tTheatrical Distributor\tMPAA Rating\tGross\tTickets Sold\tGender\n");

            for(int y = 2000 ; y <= 2015 ; y++){
                //System.out.println(String.format(url, i , gender));
                Document doc = Jsoup.connect(String.format(url, y , gender)).get();
                Element main = doc.select("#main").first();
                Element div = main.select("#page_filling_chart").get(1);
                Element center = div.select("center").first();
                Element table = div.select("table").first();
                Element tbody = div.select("tbody").first();
                Elements trs = tbody.select("tr");

                for(int i = 1 ; i < trs.size() - 2 ; i++){
                    Element tr = trs.get(i);
                    //System.out.println(tr.toString());
                    Elements tds = tr.select("td");

                    sb.append(tds.get(0).text());
                    sb.append("\t");
                    sb.append(tds.get(1).select("b").first().select("a").first().text());
                    sb.append("\t");
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, u", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(tds.get(2).select("a").first().text(), dateFormatter);
                    sb.append(date.toString());
                    sb.append("\t");
                    if(tds.get(3).select("a").first() != null){
                        sb.append(tds.get(3).select("a").first().text());
                    }
                    sb.append("\t");
                    sb.append(tds.get(4).select("a").first().text());
                    sb.append("\t");
                    sb.append(tds.get(5).text());
                    sb.append("\t");
                    sb.append(tds.get(6).text());

                    sb.append("\t");
                    sb.append(gender);

                    sb.append("\n");
                }

            }

            try (PrintWriter writer = new PrintWriter(directory.getPath()+"/" + gender + ".csv")) {
                writer.write(sb.toString());

                System.out.println("done!");

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }



}
