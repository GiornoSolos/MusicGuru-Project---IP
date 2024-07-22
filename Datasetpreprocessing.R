# Load your dataset from the CSV file
data <- read.csv("charts.csv")

# Check if data is loaded
if (is.data.frame(data) && nrow(data) > 0) {
  # Convert the dataset to tab-delimited text
  dataset_text <- paste(
    data$date, data$rank, data$song, data$artist, sep = "\t"
  )
  
  # Write the dataset to a text file
  writeLines(dataset_text, "C:/Users/Administrator/Downloads/dataset.txt")
} else {
  print("Error: No data loaded from 'charts.csv'. Check the file and its format.")
}




minimal <- paste(data$song,data$year)

# Load the required library
library(dplyr)

dataset<-data
# Function to select random songs from the dataset
getRandomSongs <- function(dataset, num_songs) {
  random_indices <- sample(length(dataset), num_songs, replace = FALSE)
  return(dataset[random_indices])
}

# Number of random songs to select
num_random_songs <- 5  # Change this to the desired number of songs

# Get random songs
random_songs <- getRandomSongs(dataset, num_random_songs)

# Output random songs to a text file
writeLines(random_songs, "random_songs.txt")

cat("Random songs saved to 'random_songs.txt'\n")


dataMinimal <- data.frame(data$artist,data$song)

# Sample dataset with position, artist, and song title
dataset <- c(
  1, "Adele", "Easy On Me",
  2, "The Kid LAROI & Justin Bieber", "Stay",
  3, "Lil Nas X & Jack Harlow", "Industry Baby",
  4, "Walker Hayes", "Fancy Like",
  5, "Ed Sheeran", "Bad Habits",
  6, "Drake Featuring Future & Young Thug", "Way 2 Sexy",
  7, "Ed Sheeran", "Shivers",
  8, "Olivia Rodrigo", "Good 4 U",
  9, "Doja Cat", "Need To Know"
)

# Function to select random songs for a given year
selectRandomSongs <- function(year, dataset, num_songs = 5) {
  year_data <- list()
  song_data <- list()
  
  for (i in seq_along(dataset)) {
    if (is.numeric(dataset[[i]]) && dataset[[i]] == year) {
      year_data <- c(year_data, list(dataset[i]))
      song_data <- c(song_data, list(dataset[i + 1:i + 2]))
    }
  }
  
  if (length(year_data) == 0) {
    return(NULL)
  }
  
  random_indices <- sample(seq_along(year_data), num_songs, replace = FALSE)
  selected_songs <- sapply(random_indices, function(i) {
    paste0(year_data[[i]], ": ", song_data[[i]][[1]], " - ", song_data[[i]][[2]])
  })
  
  return(selected_songs)
}

# Select 5 random songs per year from 2010 to 2021
output_file <- "random_songs.txt"
cat("", file = output_file)

for (year in 2010:2021) {
  random_songs <- selectRandomSongs(year, dataset, num_songs = 5)
  
  if (!is.null(random_songs)) {
    cat(paste("Year", year, "\n", random_songs, "\n\n"), file = output_file, append = TRUE)
  }
}

cat("Random songs saved to 'random_songs.txt'\n")


# Sample dataset
data <- data.frame(
date = data$date,
rank = data$rank,
song=data$song,
artist= data$artist)

getTop5ByYear <- function(data) {
  data %>%
    group_by(date) %>%
    slice(1:5)
}
top5_songs <- getTop5ByYear(data)

# Print the top 5 songs for each year
print(top5_songs)
#This code defines a function getTop5ByYear that groups the data by year and then selects the first 5 rows within each year group. It then applies this function to your sample dataset to get the top 5 songs for each year.






# Filter the dataset to get the first 5 ranked songs per year
filtered_data <- data[data$rank <= 5, ]

# Print the filtered dataset
print(filtered_data)
