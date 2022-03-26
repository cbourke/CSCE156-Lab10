# Computer Science II
## 10.0 - Java Database Connectivity API II

An introduction the Java Database Connectivity (JDBC) API in the context of a web application using the albums database.

This is a lab used in Computer Science II (CSCE 156, CSCE 156H) in the [Department of Computer Science & Engineering](https://cse.unl.edu) at the [University of Nebraska-Lincoln](https://unl.edu).

## Overview

### Resources

Prior to lab you should read/review the following resources.

1.  Review this laboratory handout prior to lab.

2.  Make sure that the Albums database is installed and available in
    your MySQL instance on CSE

### Lab Objectives & Topics

Following the lab, you should be able to:        

-   Write SQL queries to insert new data into a database using JDBC

-   Handle data integrity problems arising from bad user input and
    database integrity constraints (foreign key constraints)

-   Have further exposure to a multi-tiered application using Java
    Servlets and the HTTP request/response protocol.
    
### Peer Programming Pair-Up

To encourage collaboration and a team environment, labs will be
structured in a *pair programming* setup.  At the start of
each lab, you may be randomly paired up with another student by
a lab instructor.  One of you will be designated the *driver* 
and the other the *navigator*.  

The navigator will be responsible for reading the instructions 
and telling the driver what is to be done.  The driver will be 
in charge of the keyboard and workstation.  Both driver and 
navigator are responsible for suggesting fixes and solutions 
*together*.  Neither the navigator nor the driver is "in charge."  
Beyond your immediate pairing, you are encouraged to help and 
interact and with other pairs in the lab.

Each week you should try to alternate: if you were a driver 
last week, be a navigator next, etc.  Resolve any issues (you 
were both drivers last week) within your pair.  Ask the lab 
instructor to resolve issues only when you cannot come to a 
consensus.  

Because of the peer programming setup of labs, it is absolutely 
essential that you complete any pre-lab activities and familiarize
yourself with the handouts prior to coming to lab.  Failure to do
so will negatively impact your ability to collaborate and work with 
others which may mean that you will not be able to complete the
lab.  

## 1. Getting Started

In this lab you will continue to work with the Java Database
Connectivity API (JDBC) by adding new functionality to the Album
web application from the prior lab. 

Clone the project code for this lab from GitHub using the URL,
<https://github.com/cbourke/CSCE156-Lab10>. Refer to Lab 1.0 for
instructions on how to clone a project from GitHub.

***Note*** Be sure that the Albums database is installed and includes
the data from the SQL scripts from prior labs.


## 2. Overview 

In this lab you will add functionality to the "Add Album" button/page to
allow a user to enter an album by providing the title, band (by name),
year, and album number. Again, the focus will be on the JDBC
functionality for inserting these records, but you are invited to look
into how the HTML web form posts to an XML configured (`web.xml`) Java
Servlet which hands over the logic of inserting the album to the
database using another Plain Old Java Object (POJO), `AlbumAdder.java`.

## 3. Activities

### 3.1 Modifying Your Application

Your task is to make changes to the `AlbumAdder` class to take the four pieces of
information provided by the user and insert records in the Album
database (specifically the `Album` and the `Band` tables). Most of the
code has been provided, but you will need to implement the `addAlbumToDatabase()` 
method as specified in the JavaDoc. Keep the following in mind.

-   The Servlet provides all four arguments as *strings*. If the user does
    not enter valid data (non-integer values for year or empty
    strings), then you should handle those situations appropriately.

-   The user should not be expected to know the internal keys to a
    database. Thus the page only asks them to provide a band name. To
    preserve the integrity of data, you should not insert duplicate band
    records. Therefore, you should check to see if the Band record
    already exists (according to its name). If it does, use that foreign
    key; if it doesn’t, then you’ll also need to insert the Band record.

-   You cannot insert an album record into the `Album` table without a
    valid band ID. This is because the `Album` table has a foreign key
    reference to a band. This is good database design it ensures
    data integrity. Unfortunately, it presents a problem: we can insert
    a band record, but we need to *immediately* pull the auto-generated
    key of the inserted record so we can use it in the album record.
    
    To do this, we can specify that the statement's `executeUpdate()` 
    should retrieve the auto-generated key(s) as a result set that we
    can then examine or iterate over.  An example:

```java
String query = "INSERT RECORDS QUERY";
PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//prepare ps by setting any parameters
ps.executeUpdate();

//get the generated keys:
ResultSet keys = ps.getGeneratedKeys();

//if we only expect one:
keys.next();
int key = keys.getInt(1);

//if we expect several:
while(keys.next()) {
  int key = keys.getInt(1);
  //do something with the key
}
```

### 3.2 Running Your Application

Refer to the instructions in the previous lab for how to *preview* your
web application. Recall that you need to make changes to the `DatabaseInfo`
file to use your MySQL credentials.


## 4. Testing, Submitting & Grading

* Test your programs using the provided JUnit test suite(s).  Fix any
errors and completely debug your programs.
* Submit the following files through webhandin:
  * `AlbumAdder.java`
* Run the grader and verify the output to complete your lab.

## Advanced Activities (Optional)

1.  Every piece of data coming from a webform is represented with a string.
    The `AlbumAdderServlet` had to do the necessary conversion and handle the situation
    where invalid (non-numeric) data was provided. We could have also
    done the validation at the client tier (the web browser) to prevent bad data from
    being sent to the server. Add some Javascript to your page to
    validate the data to prevent the user from clicking the "Add" button
    unless the year and album number are valid.

2.  A Cross-Site Scripting (XSS) attack is an attack that is similar to
    an SQL injection attack that involves an attacker who injects HTML
    into a webpage by submitting it as legitimate input but the
    application fails to properly *sanitize* the HTML when it gets
    served back to the user. We will demonstrate that our webapp is
    vulnerable to this attack by hacking it.

    1.  Modify your Album database and extend the length of the `Band.name` column
        by opening your mysql client and executing the following SQL:

        ```sql
        alter table Band change column name
          `name` varchar(2048) not null default '';
        ```

    2.  Navigate to the page where you enter albums/bands and enter the
        following data:

        -   Album Title: *Yeah Ghost*

        -   Band Name (exactly as shown, but no line breaks):

            ``
            Zero 7</a></td><td>2009</td></tr></table><p>For more, 
            click <a href=
            "http://cse.unl.edu/~cbourke/cse156/passwordStealer.html">
            HERE</a><!--
            ``

        -   Album Year: 2009

        -   Album Number: 4

    3.  Submit the new album and view the list.  What do you observe
        has happened?  Click the new link at the bottom and see what happens.

    4.  This attack was not that sophisticated. Imagine injecting
        JavaScript which operates in silence and in the background and
        sends all browser data to another site and you can get an idea
        of how severe the problem can be.

    5.  Read more on how to prevent these kinds of attacks:  
        <http://en.wikipedia.org/wiki/Cross-site_scripting>  
        <https://www.owasp.org/index.php/Cross-site_Scripting_(XSS)>
        Modify your project to protect against these sort of attacks.

3.  Make this application more complete by adding more functionality so
    that users can insert songs, bands, etc. as well as delete records
    from the database.







