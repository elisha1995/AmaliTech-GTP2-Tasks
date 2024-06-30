# Text Processing and Data Management Tool

This JavaFX application provides a powerful way to manipulate and manage text data using regular expressions and Java collections.

## Features

### Regular Expression Operations

*   **Search for a pattern:** Find all occurrences of a pattern in the data collection.
*   **Replace:** Replace all occurrences of a pattern with a new string.
*   **Pattern Match:** Highlight items in the collection that match the pattern.

### Data Management

*   **Add Data:** Enter new text data into the collection.
*   **Update Data:** Modify existing entries in the collection.
*   **Delete Data:** Remove selected entries from the collection.

### User Interface

*   Intuitive JavaFX interface for easy interaction.
*   Clear display of regex results and data collection.
*   Highlighting of matching or replaced text in the collection.
*   Error handling and user-friendly error messages.

## Requirements

*   **Java Development Kit (JDK) 21:** You need to have Java installed on your system.
*   **JavaFX:** This project uses JavaFX for the user interface. Make sure you have the JavaFX library included in your project.

## Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/elisha1995/AmaliTech-GTP2-Tasks/tree/main/TextProcessor
    ```
2.  **Build the project:** If you are using a build tool like Maven or Gradle, navigate to the project directory and run the appropriate build command.
    ```bash
    mvn clean install   
    ```
    or
    ```bash
    gradlew build
    ```
3.  **Run the application:** Execute the `Main` class to launch the application.
    ```bash
    java -jar target/TextProcessingTool-1.0-SNAPSHOT.jar
    ```

## Usage

1.  **Add Data:** Type text into the "Enter data" field and click the "Add Data" button to add it to the collection.
2.  **Enter Regex Pattern:** Type a regular expression into the "Regex Pattern" field.
3.  **Perform Operations:**
    *   Click "Search" to find matches in the collection.
    *   Click "Replace" and enter the replacement text to replace matches.
    *   Click "Pattern Match" to highlight matches in the collection.
4.  **View Output:** The output area displays search results or messages about the operation.
5.  **Manage Data:** Select items in the list and use the buttons to update or delete them.

# Regular Expression Testing Examples

## Sets
- `[aeiou]` - Matches any vowel in a given text.
- `[0-9]` - Matches any digit.
- `[^aeiou]` - Matches any character that is not a vowel.

## Ranges
- `[a-z]` - Matches any lowercase letter.
- `[A-Z]` - Matches any uppercase letter.
- `[a-zA-Z]` - Matches any letter regardless of case.

## Alternations
- `cat|dog` - Matches either "cat" or "dog".
- `one|two|three` - Matches either "one", "two", or "three".
- `(red|green|blue)` - Matches either "red", "green", or "blue".

## Shorthands
- `\d` - Matches any digit (equivalent to `[0-9]`).
- `\w` - Matches any word character (equivalent to `[a-zA-Z0-9_]`).
- `\s` - Matches any whitespace character (spaces, tabs, line breaks).
- `\D` - Matches any non-digit character.
- `\W` - Matches any non-word character.
- `\S` - Matches any non-whitespace character.

## Quantifiers
- `a*` - Matches "a" zero or more times.
- `a+` - Matches "a" one or more times.
- `a?` - Matches "a" zero or one time.
- `a{3}` - Matches exactly three "a"s.
- `a{2,4}` - Matches between two and four "a"s.
- `a{2,}` - Matches two or more "a"s.

## Example Data for Testing

```plaintext
1. The quick brown fox jumps over the lazy dog.
2. My phone number is 123-456-7890.
3. Regex is fun! Do you agree?
4. AAAABBBCCCCDD
5. Red, green, and blue are primary colors.
6. This is a test sentence with numbers 12345.
7. John_Doe@example.com is a sample email address.
8. Password: P@ssw0rd!
9. The date today is 2024-06-28.
10. Call me at 987-654-3210 or 654-987-3210.
```
## Testing Examples

```plaintext
1. Sets: [aeiou] will highlight vowels in "The quick brown fox jumps over the lazy dog."
2. Ranges: [a-z] will highlight all lowercase letters in "Regex is fun! Do you agree?"
3. Alternations: cat|dog will match "dog" in "The quick brown fox jumps over the lazy dog."
4. Shorthands: \d will match digits in "My phone number is 123-456-7890."
5. Quantifiers: a+ will match "AAAA" in "AAAABBBCCCCDD".
```

