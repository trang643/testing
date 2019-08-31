<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Vocabulary Management</title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="<c:url value="/resources/styles.css" />" rel="stylesheet"
	type="text/css" />
<style>
/* Set height of the grid so .sidenav can be 100% (adjust if needed) */
.row.content {
	height: 1500px
}

/* Set gray background color and 100% height */
.sidenav {
	background-color: #f1f1f1;
	height: 100%;
}

/* Set black background color, white text and some padding */
footer {
	background-color: #555;
	color: white;
	padding: 15px;
}

/* On small screens, set height to 'auto' for sidenav and grid */
@media screen and (max-width: 767px) {
	.sidenav {
		height: auto;
		padding: 15px;
	}
	.row.content {
		height: auto;
	}
}
</style>

</head>
<body>
	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav">
				<h2>Vocabulary Easier</h2>
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="/voca/">Home</a></li>
					<li><a href="/voca/add_bulk">Add Bulk Words</a></li>
					<li><a href="/voca/test/word_english_meaning">Word -
							Enlish Meaning Test</a></li>
					<li><a href="/voca/test/sound_english_meaning">Sound -
							English Meaning Test</a></li>
					<li><a href="/voca/test/sound_vietnamese_meaning">Sound -
							Vietnamese Meaning Test</a></li>
					<li><a href="/voca/practise/sound_english_meaning">Sound -
							English Meaning Practise</a></li>
					<li><a href="/voca/test_setting">Test Setting</a></li>
				</ul>
				<br>
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search Blog..">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>

			<div class="col-sm-9">
				<c:url var="submitAction" value="/test_setting"></c:url>
				<form:form action="${submitAction}" commandName="TestSetting"
					id="form" acceptCharset="UTF-8">
					<div class="form-group">
						<form:label path="type">
							<spring:message text="Type" />
						</form:label>
						<form:select path="type" id="type" cssClass="form-control">
							<form:options items="${types}" />
						</form:select>
					</div>
					<div class="form-group">
						<form:label path="numberOfWords">
							<spring:message text="Number Of Words" />
						</form:label>
						<form:input path="numberOfWords" cssClass="form-control" />
					</div>
					<div class="form-group">
						<form:label path="numberOfWords">
							<spring:message text="Number Of Words" />
						</form:label>
						<form:checkboxes items="${topics}" path="selectedTopics"
							cssClass="form-control" itemLabel="topicName" itemValue="id" />
					</div>
					<div class="form-group">
						<form:button class="btn btn-primary">Submit</form:button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<footer class="container-fluid">
		<p>Footer Text</p>
	</footer>

</body>
</html>
