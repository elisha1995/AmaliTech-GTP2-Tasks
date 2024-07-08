# Advanced Sorting Algorithms Web Application

This project demonstrates a web application that allows users to input arrays of integers and sort them using various advanced sorting algorithms. The application uses Jakarta EE (i.e. Servlets and Jakarta Server Pages (JSP)).

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Setup Instructions](#setup-instructions)
- [Usage Instructions](#usage-instructions)


## Overview

This web application provides a user interface to input arrays of integers and sort them using different algorithms like Heap Sort, Quick Sort, Merge Sort, Radix Sort, and Bucket Sort.

## Architecture

The architecture of the application follows a layered approach, separating concerns into different components.

### Components

- **Model**: Contains the data structures used in the application (`DataEntry`).
- **DAO (Data Access Object)**: Handles data persistence (`DataEntryDAO`).
- **Service**: Contains business logic (`DataEntryService`, `SortingService`).
- **Servlets**: Handles HTTP requests and responses (`DataEntryServlet`, `SortServlet`).
- **Utilities**: Contains sorting algorithm implementations (`SortingAlgorithms`).

## Architecture Diagram
```plantuml
@startuml

package "Model" {
class DataEntry {
+ int[] data
+ getData(): int[]
+ setData(data: int[]): void
}
}

package "DAO" {
class DataEntryDAO {
+ addDataEntry(DataEntry dataEntry): void
+ getAllDataEntries(): List<DataEntry>
+ updateDataEntry(int index, DataEntry dataEntry): void
+ deleteDataEntry(int index): void
}
}

package "Service" {
class DataEntryService {
- DataEntryDAO dataEntryDAO
+ addDataEntry(int[] data): void
+ getAllDataEntries(): List<DataEntry>
+ updateDataEntry(int index, int[] data): void
+ deleteDataEntry(int index): void
}

    class SortingService {
        + sort(int[] data, String algorithm): int[]
    }
}

package "Servlets" {
class DataEntryServlet {
+ doGet(): void
+ doPost(): void
}

    class SortServlet {
        + doGet(): void
        + doPost(): void
    }
}

package "Utilities" {
class SortingAlgorithms {
+ heapSort(int[] data): void
+ quickSort(int[] data, int low, int high): void
+ mergeSort(int[] data, int left, int right): void
+ radixSort(int[] data): void
+ bucketSort(int[] data): void
}
}

DataEntryService --> DataEntryDAO : uses
DataEntryServlet --> DataEntryService : uses
SortServlet --> SortingService : uses
SortingService --> SortingAlgorithms : uses

@enduml
```

## Setup Instructions

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/elisha1995/AmaliTech-GTP2-Tasks/tree/main/AdvancedSorting
    cd AdvancedSorting
    ```

2. **Build the Project**:
   Ensure you have Maven installed. Run the following command to build the project:
    ```bash
    mvn clean install
    ```

3. **Deploy on Tomcat**:
   Copy the generated WAR file from the `target` directory to the `webapps` directory of your Tomcat installation.

4. **Start Tomcat**:
   Start your Tomcat server and navigate to `http://localhost:8080/AdvancedSorting` in your browser.

## Usage Instructions

1. **Add Data**:
   Navigate to the data entry page and input integers separated by commas. Click "Add Data".

2. **Sort Data**:
   From the list of data entries, choose a sorting algorithm from the dropdown and click "Sort".

3. **View Results**:
   The sorted array and the algorithm used will be displayed on the results page.

## Project Structure:
```css
advancedsorting/
├── docs/
│   ├── architecture-diagram.png
│   ├── components.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/advancedsorting/
│   │   │   │   ├── model/
│   │   │   │   │   └── DataEntry.java
│   │   │   │   ├── dao/
│   │   │   │   │   └── DataEntryDAO.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── DataEntryService.java
│   │   │   │   │   └── SortingService.java
│   │   │   │   ├── servlet/
│   │   │   │   │   ├── DataEntryServlet.java
│   │   │   │   │   └── SortServlet.java
│   │   │   │   ├── util/
│   │   │   │   │   └── SortingAlgorithms.java
│   │   ├── webapp/
│   │   │   ├── WEB-INF/
│   │   │   │   └── web.xml
│   │   │   ├── data-entry.jsp
│   │   │   ├── sort-result.jsp
│   │   │   ├── styles.css
│   │   │   └── sort-page-styles.css
├── README.md
├── pom.xml
```

