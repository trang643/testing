<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Vocabulary Test</title>
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
	border-color: #ccc;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #fff;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #f0f0f0;
}

.tg .tg-4eph {
	background-color: #f9f9f9
}
</style>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="<c:url value="/resources/styles.css" />" rel="stylesheet"
	type="text/css" />

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(":radio").change(function(event) {
			$("#form").submit();
		});
	});
</script>
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

				<c:url var="testAction" value="/test"></c:url>
				<c:if test="${not empty msg }">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						${msg}
					</div>
				</c:if>

				<form:form action="${testAction}" commandName="TestModel" id="form"
					acceptCharset="UTF-8">
					<c:if test="${not empty TestModel.msg }">
						<div class="alert alert-warning">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							${TestModel.msg}
						</div>

					</c:if>
					<table>
						<tr>
							<td><spring:message text="" /></td>
							<td><c:if
									test="${'SOUND_ENGLISH_MEANING' == TestModel.testType || 'SOUND_VIETNAMESE_MEANING' == TestModel.testType}">
									<audio controls autoplay="autoplay">
										<source
											src="<c:url value="/ex-resources/${TestModel.word.word}.mp3"/>"
											type="audio/mpeg">
									</audio>
								</c:if> <c:if test="${'WORD_ENGLISH_MEANING' == TestModel.testType}">
									<p style="font-weight: 700; font-size: 24px;">${TestModel.question}</p>
								</c:if></td>
						</tr>
						<tr>
							<td><form:label path="word">
									<spring:message text="" />
								</form:label></td>
							<td><form:radiobuttons path="answerUUID" htmlEscape="false"
									items="${TestModel.answerMap}" element="p"></form:radiobuttons>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
