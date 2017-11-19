package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "011120775";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String submissions_url = "https://studies.cs.helsinki.fi/ohtustats/students/"+studentNr+"/submissions";
        String courses_url = "https://studies.cs.helsinki.fi/ohtustats/courseinfo";

        String subsBodyText = Request.Get(submissions_url).execute().returnContent().asString();
        String coursesBodyText = Request.Get(courses_url).execute().returnContent().asString();


//        System.out.println("json-muotoinen data:");
//        System.out.println( coursesBodyText );

        Gson mapper = new Gson();
        Course course = mapper.fromJson(coursesBodyText, Course.class);
        Submission[] subs = mapper.fromJson(subsBodyText, Submission[].class);

        System.out.println("opiskelijanumero: " + studentNr);
        int hours = 0, tasks = 0;
        for (Submission submission : subs) {
            submission.setCourse(course);
            System.out.println(submission);
            hours += submission.getHours();
            tasks += submission.getExerciseCount();
        }
        System.out.println("yhteensä: " + hours + " tuntia, " + tasks + " tehtävää");

    }
}