<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.advancedsorting.model.DataEntry" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sorting Algorithms</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h1>Advanced Web Sorting Algorithms</h1>

        <%
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
        <div class="error-message">
            <%= errorMessage %>
            <% session.removeAttribute("errorMessage"); %>
        </div>
        <%
            }
        %>

        <div id="data-area">
            <h2>Data Entries:</h2>
            <form action="${pageContext.request.contextPath}/dataEntry" method="post">
                <input type="hidden" name="action" value="add">
                <label>
                    <em><strong>NB:</strong> Input integers only separated by commas with no space after the commas <br></em>
                    <hr>
                    <input type="text" name="data" placeholder="Enter integers ">
                </label>
                <input type="submit" value="Add Data">
            </form>
            <hr>
            <table>
                <tr>
                    <th>Index</th>
                    <th>Data</th>
                    <th>Actions</th>
                </tr>
                <%
                    List<DataEntry> dataEntries = (List<DataEntry>) request.getAttribute("dataEntries");
                    if (dataEntries != null) {
                        for (int i = 0; i < dataEntries.size(); i++) {
                            DataEntry entry = dataEntries.get(i);
                %>
                <tr>
                    <td><%= i %></td>
                    <td><%= entry %></td>
                    <td>
                        <form action="dataEntry" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="index" value="<%= i %>">
                            <label>
                                <input type="text" name="data" placeholder="New data">
                            </label>
                            <input type="submit" value="Update">
                        </form>
                        <form action="dataEntry" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="index" value="<%= i %>">
                            <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this entry?');">
                        </form>
                        <form action="sort" method="post" style="display:inline;">
                            <input type="hidden" name="index" value="<%= i %>">
                            <label>
                                <select name="algorithm">
                                    <option value="heapSort">Heap Sort</option>
                                    <option value="quickSort">Quick Sort</option>
                                    <option value="mergeSort">Merge Sort</option>
                                    <option value="radixSort">Radix Sort</option>
                                    <option value="bucketSort">Bucket Sort</option>
                                </select>
                            </label>
                            <input type="submit" value="Sort">
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </body>
</html>