import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList <String> allCastMembers = new ArrayList<String>();

        for (Movie movie : movies) {
            String[] castMembers = movie.getCast().split("\\|");
            for (String castMember : castMembers) {
                if (!allCastMembers.contains(castMember)) {
                    allCastMembers.add(castMember);
                }
            }
        }

        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine();

        ArrayList <String> resultCast = new ArrayList <String>();
        for (String castMember : allCastMembers) {
            if (castMember.toLowerCase().contains(searchTerm.toLowerCase())) {
                resultCast.add(castMember);
            }
        }

        resultCast.sort(null);

        for (int i = 0; i < resultCast.size(); i++) {
            int num = i + 1;
            System.out.println(num + ". " + resultCast.get(i));
        }

        System.out.println("Which cast member you're interested in? ");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCastMember = allCastMembers.get(choice - 1);
        ArrayList <Movie> resultsMovie = new ArrayList <Movie>();
        for (Movie movie : movies) {
            if (movie.getCast().contains(selectedCastMember)) {
                resultsMovie.add(movie);
            }
        }

        sortResults(resultsMovie);

        // now, display them all to the user
        for (int i = 0; i < resultsMovie.size(); i++)
        {
            String title = resultsMovie.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = resultsMovie.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList <Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            // Put the keywords in an array
            String[] movieKeywords = movies.get(i).getKeywords().split("\\|");

            // To break the loop when the keyword match the search term
            boolean found = false;

            // To transverse the array
            int index = 0;

            while (!found && index < movieKeywords.length) {
                if (movieKeywords[index].contains(searchTerm)) {
                    results.add(movies.get(i));
                    found = true;
                } else {
                    index ++;
                }
            }
        }

        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listGenres()
    {
        ArrayList <String> allGenres = new ArrayList<String>();

        for (Movie movie : movies) {
            String[] genres = movie.getGenres().split("\\|");
            for (String genre : genres) {
                if (!allGenres.contains(genre)) {
                    allGenres.add(genre);
                }
            }
        }

        allGenres.sort(null);

        for (int i = 0; i < allGenres.size(); i++) {
            int num = i + 1;
            System.out.println(num + ". " + allGenres.get(i));
        }

        System.out.println("Which genre you're interested in? ");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = allGenres.get(choice - 1);
        ArrayList <Movie> resultsMovie = new ArrayList <Movie>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(selectedGenre)) {
                resultsMovie.add(movie);
            }
        }

        sortResults(resultsMovie);

        // now, display them all to the user
        for (int i = 0; i < resultsMovie.size(); i++)
        {
            String title = resultsMovie.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = resultsMovie.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        movies.sort(new UserRatingComparator());

        ArrayList<Movie> top50Movies = new ArrayList<Movie>(movies.subList(0, 50));

        for (int i = 0; i < top50Movies.size(); i++) {
            int num = i + 1;
            System.out.println(num + ". " + top50Movies.get(i).getTitle() + ": " + top50Movies.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50Movies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}