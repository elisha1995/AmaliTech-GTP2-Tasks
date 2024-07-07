<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Arrays" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sorting Result</title>
        <link rel="stylesheet" type="text/css" href="sort-page-styles.css">
    </head>
    <body>
        <div class="container">
            <h1>Sorting Result</h1>

            <%
                int[] unsortedData = (int[]) request.getAttribute("unsortedData");
                int[] sortedData = (int[]) request.getAttribute("sortedData");
                String algorithm = (String) request.getAttribute("algorithm");

                String info;
                switch(algorithm) {
                    case "heapSort":
                        info = "Heap Sort is a comparison-based sorting algorithm that uses a binary heap data structure. It has a time complexity of O(n log n) and is not stable.";
                        break;
                    case "quickSort":
                        info = "Quick Sort is a divide-and-conquer algorithm that picks an element as pivot and partitions the array around the pivot. It has an average time complexity of O(n log n) but can degrade to O(n^2) in worst case.";
                        break;
                    case "mergeSort":
                        info = "Merge Sort is a divide-and-conquer algorithm that divides the input array into two halves, recursively sorts them, and then merges the two sorted halves. It has a time complexity of O(n log n) and is stable.";
                        break;
                    case "radixSort":
                        info = "Radix Sort is a non-comparative integer sorting algorithm that sorts data with integer keys by grouping keys by individual digits. It has a time complexity of O(nk) where k is the number of digits in the largest number.";
                        break;
                    case "bucketSort":
                        info = "Bucket Sort is a distribution sort that works by distributing the elements of an array into a number of buckets. Each bucket is then sorted individually. It has an average time complexity of O(n+k) where k is the number of buckets.";
                        break;
                    default:
                        info = "Information not available.";
                }
            %>

            <div>
                <h2>Unsorted Array:</h2>
                <p><%= Arrays.toString(unsortedData) %></p>
            </div>

            <div>
                <h2>Sorted Array:</h2>
                <p><%= Arrays.toString(sortedData) %></p>
            </div>

            <div>
                <h2>Algorithm Used:</h2>
                <p><%= algorithm %></p>
            </div>


            <div class="algorithm-info">
                <h3>Algorithm Information</h3>
                <p><%= info %></p>
            </div>

            <a href="${pageContext.request.contextPath}/dataEntry" class="home-button">Back to Data Entry</a>
        </div>
    </body>
</html>
