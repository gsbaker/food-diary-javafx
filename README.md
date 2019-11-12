---


---

<h1 id="food-diary">Food Diary</h1>
<p>This project began as my A Level Computer Science ‘programming project’. I have continued developing this project since then and my overall goal is to learn through trying to implement more features.</p>
<h2 id="how-to-install">How to install</h2>
<ul>
<li>Clone this repo.</li>
<li>You will need to create a mysql database under ‘myuser’@‘localhost’ called <strong>fooddiary</strong>.<br>
<code>create database fooddiary;</code></li>
<li>You can use a different user to create the db, but you’ll have to change the username variable in the dao.</li>
<li>Then create a table called <strong>food</strong>.<br>
<code>use fooddiary;</code><br>
<code>create table food (id int, name varchar(255), calories int);</code></li>
<li>If you want, you can insert all the values for foods here.</li>
<li>If not, you can add them in the actual program itself once it’s running under the ‘Add Food’ section.</li>
</ul>

