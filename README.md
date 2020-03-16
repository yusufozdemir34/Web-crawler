# Web-crawler
write a program in Java which counts top Javascript libraries used in web pages found on Google.

The command-line program should do the following:
0) Read a string (search term) from standard input
1) Get a Google result page for the search term
2) Extract main result links from the page
3) Download the respective pages and extract the names of Javascript libraries used in them
4) Print top 5 most used libraries to standard output

Bonus steps
- write tests or think about the approach for testing your code
- think about / implement Java concurrency utilities to speed up certain tasks
- think about / implement deduplication algorithms for the same Javascript libraries with different names
