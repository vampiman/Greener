# Greener
'Greener' is a Java-based Desktop application designed to give the users insight into ways of reducing their CO2 emissions and encouraging them to do so by providing proper carbon calculations, the possibility of sharing their struggle for the environment with others and using gamification techniques to make the use of the application enjoyable. 

# Index
- [Project Setup](#project-setup)
- [Irem Ugurlu](#irem-ugurlu)
- [Jaron Rosenberg](#jaron-rosenberg)
- [Natalia Struharova](#natalia-struharova)
- [Robert Mînea](#robert-mînea)
- [Nick Ouwerkerk](#nick-ouwerkerk)
- [Mayasa Quacqess](#mayasa-quacqess)
- [Lee Chen](#lee-chen)


# CSE1105 Template Repository

This a template repository to help you get started on making the best project possible!

You can download the latest version [here](https://github.com/SERG-Delft/TI1216/releases)

http://stackoverflow.com/a/6466993

## How to import into your IDE

Eclipse:
[http://javapapers.com/java/import-maven-project-into-eclipse/](http://javapapers.com/java/import-maven-project-into-eclipse/)

Intellij:  
[https://www.jetbrains.com/help/idea/2016.2/importing-project-from-maven-model.html](https://www.jetbrains.com/help/idea/2016.2/importing-project-from-maven-model.html)

<!--## Getting your weekly reports-->

<!--**Jacoco**:  -->
<!--Run `maven install` ([Intellij](https://www.jetbrains.com/help/idea/2016.3/getting-started-with-maven.html#execute_maven_goal)/[Eclipse](http://imgur.com/a/6q7pV))-->

<!--**Checkstyle**:  -->
<!--Run `maven site`-->

# Project Setup
For the setup of this project you will need to install the following:
- [GlassFish 5.0 - Full Platform](https://javaee.github.io/glassfish/download) (just download it and remember the file where you put it)
- [MySQL](https://dev.mysql.com/downloads/windows/installer/8.0.html) (MySQL Workbench is preffered for Project Management,)
- [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Database Creation
- You  need to create a User 'sammy' with Password 'temporary' for your database. 

Create a new user by running the following scripts on the database or create the user through Mysql Workbench(easier) :

CREATE USER 'sammy'@'localhost' IDENTIFIED BY 'temporary';

GRANT ALL PRIVILEGES ON * . * TO 'sammy'@'localhost';

FLUSH PRIVILEGES;
<!--- You can see how that's done [here](https://dev.mysql.com/doc/refman/8.0/en/creating-accounts.html).-->
- Don't forget to create the user 'sammy', as mentioned above
- Create a new database named 'greener'.
- In this database, create a table named 'person' with the following DDL script:

CREATE TABLE `person` (
  `ID` double NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Password` varchar(150) NOT NULL,
  `Name` varchar(40) NOT NULL,
  `Friend_code` varchar(30) DEFAULT NULL,
  `Level` int(11) NOT NULL DEFAULT '1',
  `CO_2_saved` double NOT NULL,
  `Vegan_meal` double NOT NULL,
  `Bike` double NOT NULL,
  `Solar_panels` double NOT NULL,
  `Local_produce` double NOT NULL,
  `Lowering_home_temperature` double NOT NULL,
  `Public_transport` double NOT NULL,
  `Achievements` varchar(24) NOT NULL DEFAULT '000000000000000000000000',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `Friend_code_UNIQUE` (`Friend_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

- In this database, create a table 'friends' with the following DDL script:

CREATE TABLE `friends` (
  `ID` int(11) NOT NULL,
  `User_email` varchar(40) NOT NULL,
  `Friend_email` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`,`Friend_email`),
  KEY `User_email_idx` (`User_email`),
  CONSTRAINT `User_email` FOREIGN KEY (`User_email`) REFERENCES `person` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

## Server Setup

- **Don't forget** to go to File -> Project Structure.. and select jdk 1.8
- Furthermore you need to have a working version of [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and add it as a project sdk by going to File -> Project Structure -> Project SDK (select New.).
- After installing, you should download and import the project into your Intellij IDE.
- You should add this as a maven project.
- Once you're set up, run 'maven clean' and 'maven install'.
- You will have to set up GlassFish as follows:
- On the left of the 'Run' button of Intellij, there is a dropdown to select the run configuration, click on it then head to Edit Configurations... -> Add New Configuration ( the '+' button in the upper left corner of the window) -> GlassFish Server -> Local, if it is the first time you use Glassfish you will have to click 'Configure.' at the top of this window and navigate to the location where you downloaded the Glassfish Server files.
- Set JRE to 1.8 in this window
- Set Server Domain to domain1 in this window
- Go to Deployment tab of your configuration window, Add Artifact..('+' button on the right)->Artifact and set to Server:war
- You can start the server by clicking run now.
- **Make sure** that your database process is running, and you have created the user 'sammy' and the tables accordingly.
- **Once the server is running**, you can start the application by running the Main class from the Client module (modules/Client/src/main/java/gui/Main.java).

## Getting the test coverage
- Run "mvn site" then check targer/site/cobertura in the Server module for the Server coverage and in the Client module for the Client coverage.

## Getting the checkstyle report
- Run "mvn checkstyle:checkstyle" and check the site file of each module for the checkstyle-result.xml or just use the Intellij Checkstyle plug-in.

# Irem Ugurlu
**(4851625, iugurlu)**

## Personal Development Plan

<img src="https://user-images.githubusercontent.com/47633984/52824577-6601fc80-30b9-11e9-9d03-5eda021d9827.jpg" width="120"/>

Strong points:
- I am ambitious and in order to reach my goal I can study a lot
- When I have a task and I have promised to finish it, I finish it
- In a team, hard work and being aware of the responsibilities can be useful

Weaker Points:
- Sometimes, because of a high workload I can be stressed
- I don’t think this is going to have an effect on the team

<img src="https://user-images.githubusercontent.com/47633984/52824182-f63f4200-30b7-11e9-9186-03209eedabed.png" width="700"/>

### G-Goal
- Developing my java skills
- Learning how a group project works 
- Getting a good grade
These are important because developing my knowledge about java and learning how to work efficiently in a group are useful for my future education and career
I think I will achieve this goals at the end of this project because when I finish this project I am going to have an experience about group project and also hopefully I am going to learn a lot more about java

### R-Reality
This is my first project and we just started so I hope it goes well and adds a lot of things to me

### O-Options
The option I hope is finishing this project successfully and I think we are going to work efficiently as a group and at the end hopefully we will finish this project successfully with a good grade. 

### W-Will
- I am going to work a lot in order to reach my goal and I am going to start right now for this. The first step I can take is making a plan with my group mates and then starting our project according to this plan
- Action Plan:
-- Determining the things which we are going to do
-- Deciding important parts as a group (such as which libraries we are going to use etc.)
-- Distributing work equally to members and assigning a task to each group member
-- Starting to code
-- While everyone is working on their part, informing each other about our progress, also we need to be careful about the parts which are connected (two person’s task can be connected so they should act according to this otherwise it can lead a non-compiling code)


# Jaron Rosenberg
**(4839641, jrosenberg)**

<img src="https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-9/42417325_1352297734900788_1361717405511843840_n.jpg?_nc_cat=107&_nc_ht=scontent-amt2-1.xx&oh=8716ec977519897a0b009fce9e2feaae&oe=5CE432D9" width="200"/>

## Personal Development Plan

### Strong points:
-	Hard working.
-	Like to take the lead.

### Examples:
-	Sending an e-mail out to create a WhatsApp group.
-	Telling people what I think of something.

### How is shown in a team:
-	Usually positive when I give feedback.
-	Can cause conflict when our opinions differ.

### Core quadrant:
...

## Plan of action
### Goal:
-	Learn to work with and as a team.
-	Learn to e.g. read and test other people’s code.
-	This is important, since this is how companies run.
-	This will be succeeded when we have a final result that we all worked on and can explain.

### Reality:
-	I’m doing my best to work well to reach that goal.
-	So far my results are not bad. I’m working hard to pass as much as possible.
-	I can improve my presentation skills.

### Options:
-	Work as much as possible with my group.
-	Try to be with the entire group at every lecture/group session.
-	I can’t decide for others t show up.

### Will:
-	I’ll try to actively give everyone a clear task in the group.
-	During the week through WhatsApp and during the oopp group sessions.
-	Make contact with everyone from my group.
-	Answer to my mail.
-	Yes.


# Natalia Struharova 
**(4935519, nstruharova)**

### <img src="https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-9/11232123_821378501308705_164252636500004083_n.jpg?_nc_cat=109&_nc_ht=scontent-amt2-1.xx&oh=00662980269ef215ffc863494dcfeb6c&oe=5CE4D39B">

## Personal Development plan

### Strengths
- Hardworking: I work hard and like to take on challenges. I rarely find myself giving up on any goal I have set for myself.
- Reliable: I consider my self reliable (punctuality, delivery of promised work, etc.) when it comes to working, especially in a team, where people have to rely on each other.

### Weaknesses

- Spending too much time on everything: I am hardworking, but sometimes I find that I spent too much time on an issue that may not even be proportionally important. I try to tweak it to perfection and end up losing too much time. It’s hard for me to let things the way they are if I don’t feel they don’t look perfect to me.
- Inability to work in a team: I also find it difficult to work in a team, as I am used to working alone on most of my projects. I usually like everything my way and sometimes push my ideas forward too intensely.

### Goals

What therefore I hope to achieve during the OOP Project is improving in computer science, namely serverside programming, and my communicational skills within a team.

### Motivation behind these goals

In the future I would like to work in the field of computer science. I know I will be working with people, and that’s the reason why working in a team is my priority skill to improve. I believe that an efficient team can really make a great job at delivering the best product possible.

Despite the fact that most of my future projects may be group work, I would like to posses the skills to be able to support any part of development. The thing I mainly lack in general knowledge is the serverside-side programming, which I hope to learn about and practice during OOP Project.

### Current stage

Currently I am not getting much teamwork experience outside school, but while at school I have started improving by teaming up with other people, even people I didn’t know before, and managed to make everything work. Our communication and results were the best they could have been in all of our group assignments. I think I am on the right track, and I expect OOP to help me even more.

In terms of the serverside-side programming skill, I haven’t coded it from scratch, I use libraries that take care of this when I am working on my personal projects, such as the iOS game applications. So far I have encountered serverside programming at school only at Web & Database Technology course when programming a web game, but again, I hope that OOP Project will offer me more room to practice this skill.

### Course of action 

I already started communicating with my team in order to set our general course of action. In terms of serverside side programming, I already started my research on how to create a serverside and integrate its workings into the app. I hope to be able to maintain my well-balanced time management and, while this will be my priority alongside other subjects, work on my other projects as well.


# Robert Mînea
**(4848993, rminea)**

<img src="https://scontent-ams3-1.xx.fbcdn.net/v/t1.0-9/35476812_1970122483058968_6927082946167832576_n.jpg?_nc_cat=100&_nc_ht=scontent-ams3-1.xx&oh=ad9d096c72b9f03b8661e09477656f0b&oe=5CDFD1FD" width="400"/>

## Personal Development Plan

### Strong Points:
- Obsession of finishing tasks/assignments as early as possible
- Determined to work hard when having in mind the image of a final product that can be useful for me/others
- I love learning/developing new skills

### Weak Points:
- I tend to avoid putting my trust in other people because I am afraid that they might negatively influence my work (on purpose or by mistake)
- I usually follow the "cowboy coding" type of programming, without planning too much in advance

### Goals:
- Learn more about developing a project in a professional manner
- Doing tests in a more extensive way, such that they are useful for the team, ensuring that the code is well done

### Reality:
- I am trying to switch to a more plan-oriented style of programming in order to work in a more organized manner, rather than going back and forth through the code to add details
- I think that working in a team will contribute in switching to the style mentioned above

### Options:
- For now I am excited to work in a larger group since it will require more planning to maintain order, and it is exactly what I want for now, getting better in working in larger teams with many different people since I may encounter similar situations in my career.

### Will:
- I want to finish the project knowing that everyone did a great work and that I did a good job in assuring my teammates that my part has been done successfully.
- I also want to see other people's style of working/coding in order to exchange skills and knowledge


# Nick Ouwerkerk
**(4957032, nouwerkerk)**

<img src="/uploads/206bb69fb21553e2b8cb0a409cfd915a/foto_Nick.jpg" width = "200">

## Personal Development Plan

### Strong points:
 - I like to stick to a schedule / I am structural.
 - I am dedicated and like to make my work as perfect as possible.

Examples:
 - I usually make a (imaginary) schedule to plan parts of my day.
 - I mostly am not happy with ‘good enough’, I’ll do my work until I’m personally satisfied with it and it adheres to my personal standards.

How this point is shown in a team:
 - If working in a team, I can stick to the schedule given to me and plan the work.
 - I try to make my part of the work as I possibly can.

### Weak points:
 - Constant and clear communication.
 - Spending to much time on one element when this is not needed.

Examples
 - I can have problems notifying about problems on time.
 - I could spend hours on minor features whereas this time could probably be spent more wisely.

How this point is shown in a team:
 - In a team, communicating is extremely important for a good workflow. I should definitely improve on this front.
 - Instead of spending to much time on my own work, I could try to help others.


### G - Goal
 - I want to improve my communication skills. I want to communicate more frequently and communicate about my current status in relation to the project. I would also like to improve the way I code when working with others.
 - This is important to me because communication is key in such a large project, and since I have had problems with communication in the past which made project unnecessarily hard, I want to improve my communication skills to be able to make the project work. I do not have experience with coding in a team yet.
 - I feel like I have achieved this goal when the project is done and the team has put out a product which we are satisfied with.

### R – Reality
 - 	I try to make my code as clear and readable as possible. For communication I try to clear and precise.
 - 	My code is usually considered quite well-structured. One the other hand. My communication leaves a lot to be desired, but I seem to be considered good in explaining.
 - 	I could add more comments to further explain the code. I could also communicate more often and be more concrete about the problems I am facing.

### O – Options
 - 	For communication I could be more pro-active with communicating which problems I am facing. For the code I could start making it a habit to add more comments.
 - 	Definitely be more proactive, since if the problems are identified earlier, they are easier to solve.
 -	Being a bit laid back when solving problems, waiting too long with trying to solve the problems I’m facing.

### W - Will
 - Look up examples of good code, get an idea of what ‘good code’ consists of. Also communicate more during meetings and not try to solve to much by myself.
 - 	I am going to do this from the first meeting until the end of the project.
 - 	At this moment, I can already express my thoughts towards the project.
 - 	I definitely feel like these are problems that I have to solve myself. I can however take note of the coding style of my teammate and learn from this. 
 - 	I do not think making an “action plan” would be helpful to achieve my goals. Since it are goals that are to be achieve every week and should not be ‘build up’ until the end of the project, I think it would be of much more help if I would evaluate every week if I feel like I communicated well enough and if there are points I could improve on.

## Mayasa Quacqess
**(4898109, mquacqess)**

## Personal Development Plan

<img src="https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/1609/avatar.png?width=400"/>

### Strong points:
- Communication.
- Planning.
- Self-motivation.

### Week points:
- Taking too many risks.
- impatience.
- Public speaking.


### G-Goal
- Learn how to plan and execute a project within a team.
- Getting used to writing code tests.
- Develope my public speaking skills.


### R-Reality:
- Trying to communicate with my team mates.
- As soon as we start coding I'll try my best to practice writing test for my code.
- I'll make sure to finish my tasks on time and to communicate efficiently with my team mate. 
 

### Options:
- Collaborating with the team to meet the milestones we set.
- Presenting our appliction during the demos.	


### Will:
- Spend enough time practicing the soft and hard skills needed to complete this project successfully.
- During our team meeting and whenever I have a task to handin.
- Try to communicate with my team meet as efficient as possible.
- By speaking thier ideas and thoughts outload.

# Lee Chen
**(4703812, lschen)**
![Lee](https://www.instagram.com/p/BtOmE1fgBWw/)

## Personal Development Plan
### What are two of my strong points?
- I keep my promises.
- I am excited to try out new thinks.
I hope my team reflects me as a productive person. 

### What are two of my weaker points?
- My English is not the best.
- I am not that creative.
In a team they might think I am more of a lazy person in one of the first meetings.

### G. goal
I hope to learn how to work with new people and get inspiration from other people, 
this is important to me because I am sometimes not capable of really expressing myself in a new environment. 
I think I have achieved this goal when I don't feel nervous with big projects including new people. 
Not only will this help me in my future career, but also in every day life.

### R. reality
I am trying to be more of a social person day by day, fortunatly I have met some nice people on campus, which are also my study partners.
I talk less when I have to say things in English.

### O. options
I now have another chance to be more social in a new groups. Take my chances this time.
The only think that holds is, I don't like to come out of my comfort zone.

### W. Will
To reach my goal I think I have to come up with usefull plans for my team, 
brainstorm about new ideas for the project that is usefull for my teammates before a meeting starts.
Right now I am going to spend my weekend learning Git. If I get stuck I can always ask my team for help.

