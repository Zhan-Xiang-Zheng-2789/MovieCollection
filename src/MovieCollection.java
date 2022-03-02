import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    askMovie(results);
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
    ArrayList<String> casts = new ArrayList<>();
    System.out.print("Enter a person to search for (first or last name): ");
    String searchChar = scanner.nextLine();
    String searchChar2 = searchChar.substring(0,1).toUpperCase() + searchChar.substring(1);
    for(Movie movie : movies) //adds all casts with user input name to an array
    {
      String[] castsFromMovie = movie.getCast().split("\\|");
      for(String cast : castsFromMovie)
      {
        if (!(casts.contains(cast)) && (cast.contains(searchChar) || cast.contains(searchChar2))) //making sure no duplicates are copied
        {
          casts.add(cast);
        }
      }
    }

    Collections.sort(casts); //organize the casts alphabetically

    for (int i = 0; i < casts.size(); i++) //prints out the casts
    {
      System.out.println("" + (i+1) + ". " + casts.get(i));
    }

    System.out.println("Which actor would you like to learn more about?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    ArrayList<Movie> castMovies = new ArrayList<>(); // adds cast' movie
    for(Movie movie : movies)
    {
      if(movie.getCast().contains(casts.get(choice-1)))
      {
        castMovies.add(movie);
      }
    }

    sortResults(castMovies);

    askMovie(castMovies);

  }

  private void searchKeywords()
  {
    {
      System.out.print("Enter a keyword term: ");
      String searchTerm = scanner.nextLine();

      // prevent case sensitivity
      searchTerm = searchTerm.toLowerCase();

      // arraylist to hold search results
      ArrayList<Movie> results = new ArrayList<Movie>();

      // search through ALL movies in collection
      for (int i = 0; i < movies.size(); i++)
      {
        String getKeywords = movies.get(i).getKeywords();
        getKeywords = getKeywords.toLowerCase();

        if (getKeywords.contains(searchTerm))
        {
          //add the Movie objest to the results list
          results.add(movies.get(i));
        }
      }

      // sort the results by title
      sortResults(results);

      askMovie(results);
    }
  }

  private void listGenres()
  {
    ArrayList<String> genres = new ArrayList<>();
    for(Movie movie : movies) //adds all genre with user input name to an array
    {
      String[] genreFromMovie = movie.getGenres().split("\\|");
      for(String cast : genreFromMovie)
      {
        if (!(genres.contains(cast))) //making sure no duplicates are copied
        {
          genres.add(cast);
        }
      }
    }

    Collections.sort(genres); //organize the casts alphabetically

    for (int i = 0; i < genres.size(); i++) //prints out the casts
    {
      System.out.println("" + (i+1) + ". " + genres.get(i));
    }

    System.out.println("Which genre would you like to learn more about?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    ArrayList<Movie> genreMovies = new ArrayList<>(); // adds genre movies
    for(Movie movie : movies)
    {
      if(movie.getGenres().contains(genres.get(choice-1)))
      {
        genreMovies.add(movie);
      }
    }

    sortResults(genreMovies);

    askMovie(genreMovies);

  }

  private void listHighestRated()
  {
    ArrayList<Movie> tempArray = new ArrayList<>(movies);

    tempArray.sort((o1, o2)
            -> Double.compare(o2.getUserRating(), o1.getUserRating()));

    ArrayList<Movie> highestRatedArray = new ArrayList<>();

    for(int i = 0; i < 50; i++)
    {
      highestRatedArray.add(tempArray.get(i));
    }

    for (int i = 0; i < highestRatedArray.size(); i++)
    {
      String title = highestRatedArray.get(i).getTitle();
      double userRating = highestRatedArray.get(i).getUserRating();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + userRating);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = highestRatedArray.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRevenue()
  {
    ArrayList<Movie> tempArray = new ArrayList<>(movies);

    tempArray.sort((o1, o2)
            -> Double.compare(o2.getRevenue(), o1.getRevenue()));

    ArrayList<Movie> highestRevenueArray = new ArrayList<>();

    for(int i = 0; i < 50; i++)
    {
      highestRevenueArray.add(tempArray.get(i));
    }

    for (int i = 0; i < highestRevenueArray.size(); i++)
    {
      String title = highestRevenueArray.get(i).getTitle();
      double revenue = highestRevenueArray.get(i).getRevenue();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + revenue);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = highestRevenueArray.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void importMovieList(String fileName)
  {
    {
      try
      {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        movies = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null)
        {
          // import all cells for a single row as an array of Strings,
          // then convert to ints as needed
          String[] movieFromCSV = line.split(",");

          // pull out the data for this cereal
          String title = movieFromCSV[0];
          String cast = (movieFromCSV[1]);
          String director = (movieFromCSV[2]);
          String tagline = (movieFromCSV[3]);
          String keywords = (movieFromCSV[4]);
          String overview = (movieFromCSV[5]);
          int runtime = Integer.parseInt(movieFromCSV[6]);
          String genres = (movieFromCSV[7]);
          double userRating = Double.parseDouble(movieFromCSV[8]);
          int year = Integer.parseInt(movieFromCSV[9]);
          int revenue = Integer.parseInt(movieFromCSV[10]);

          // create Cereal object to store values
          Movie nextMovie = new Movie(title,cast,director,tagline,keywords,overview,runtime,genres,userRating,year,revenue);

          // adding Cereal object to the arraylist
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

  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary
  private void askMovie(ArrayList<Movie> moviesToPrint)
  {
    for (int i = 0; i < moviesToPrint.size(); i++)
    {
      String title = moviesToPrint.get(i).getTitle();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = moviesToPrint.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
}