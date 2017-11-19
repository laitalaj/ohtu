package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "011120775";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String submissions_url = "https://studies.cs.helsinki.fi/ohtustats/students/" + studentNr + "/submissions";
        String courses_url = "https://studies.cs.helsinki.fi/ohtustats/courseinfo";
        String stats_url = "https://studies.cs.helsinki.fi/ohtustats/stats";

        String subsBodyText = Request.Get(submissions_url).execute().returnContent().asString();
        String coursesBodyText = Request.Get(courses_url).execute().returnContent().asString();


//        System.out.println("json-muotoinen data:");
//        System.out.println(coursesBodyText);

        Gson mapper = new Gson();
        Course course = mapper.fromJson(coursesBodyText, Course.class);
        Submission[] subs = mapper.fromJson(subsBodyText, Submission[].class);


        System.out.println("Kurssi: " + course + "\n");
        System.out.println("opiskelijanumero: " + studentNr);
        int hours = 0, tasks = 0;
        for (Submission submission : subs) {
            submission.setCourse(course);
            System.out.println(submission);
            hours += submission.getHours();
            tasks += submission.getExerciseCount();
        }
        System.out.println("yhteensä: " + hours + " tuntia, " + tasks + " tehtävää\n");

        String statsResponse = Request.Get(stats_url).execute().returnContent().asString();

        JsonParser parser = new JsonParser();
        JsonObject parsedData = parser.parse(statsResponse).getAsJsonObject();
        int submissions = 0, exercises = 0;
        for (int week = 1; week <= course.getWeek(); week++) {
            JsonObject elem = parsedData.get(Integer.toString(week)).getAsJsonObject();
            submissions += elem.get("students").getAsInt();
            exercises += elem.get("exercise_total").getAsInt();
        }
        System.out.println("kurssilla yhteensä " + submissions + " palautusta, palautettuja tehtäviä " + exercises + " kpl");

    }
}