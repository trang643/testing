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

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$("#word").change(function(event) {
			$("#needPrefill").val(true);
			$("#form").submit();
		});

		$(".btn-primary").click(function(event) {
			$("#needPrefill").val(false);
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
				<c:url var="addAction" value="/"></c:url>
				<form:form action="${addAction}" commandName="word" id="form"
					acceptCharset="UTF-8">
					<c:if test="${word.id > 0}">
						<div class="form-group">
							<form:label path="id">
								<spring:message text="ID" />
							</form:label>
							<form:input path="id" readonly="true" size="8" disabled="true"
								cssClass="form-control" />
							<form:hidden path="id" />
						</div>
					</c:if>
					<div class="form-group">
						<form:label path="word">
							<spring:message text="Word" />
						</form:label>
						<form:input path="word" id="word" cssClass="form-control" />
					</div>
					<div class="form-group">
						<form:label path="enMeaning">
							<spring:message text="Meaning in English" />
						</form:label>
						<form:textarea path="enMeaning" htmlEscape="false" id="enMeaning"
							cssClass="form-control" cssStyle="height: 150px" />
					</div>
					<div class="form-group">
						<form:label path="viMeaning">
							<spring:message text="Meaning in Vietnamese" />
						</form:label>
						<form:textarea path="viMeaning" htmlEscape="false" id="viMeaning"
							cssClass="form-control" cssStyle="height: 150px" />
					</div>
					<div class="form-group">
						<form:label path="example">
							<spring:message text="Example" />
						</form:label>
						<form:textarea path="example" cssClass="form-control" />
					</div>
					<c:if test="${!empty word.word and !word.needPrefill}">
						<input type="button" value="<spring:message text="Edit Word"/>"
							class="btn btn-primary" />
					</c:if>
					<c:if test="${empty word.word or word.needPrefill}">
						<input type="button" value="<spring:message text="Add Word"/>"
							class="btn btn-primary" />
					</c:if>
					<div>${audioHtml}</div>
					<div>${imageHtml}</div>

					<form:hidden path="needPrefill" id="needPrefill" />
				</form:form>
				<br>
				<h3>Words List</h3>
				<c:if test="${!empty listWords}">
					<table
						class="table table-striped table-bordered table-hover table-condensed">
						<tr>
							<th width="80">Word ID</th>
							<th width="120">Word</th>
							<th width="120">Meaning</th>
							<th width="120">Vi Meaning</th>
							<th width="120">Example</th>
							<th width="120">Sound</th>
							<th width="120">Image</th>
							<th width="60">Edit</th>
							<th width="60">Delete</th>
						</tr>
						<c:forEach items="${listWords}" var="word">
							<tr>
								<td>${word.id}</td>
								<td>${word.word}</td>
								<td>${word.enMeaning}</td>
								<td>${word.viMeaning}</td>
								<td>${word.example}</td>
								<td>
									<%-- <c:if test="${!empty word.mp3FileName}">
								<audio controls>
									<source src="<c:url value="/ex-resources/${word.word}.mp3"/>"
										type="audio/mpeg">
								</audio>
							</c:if> --%>
								</td>
								<td><img
									src="<c:url value="/ex-resources-img/${word.image}"/>"
									height="100px" width="100px" /></td>
								<td><a href="<c:url value='/edit/${word.id}' />">Edit</a></td>
								<td><a href="<c:url value='/remove/${word.id}' />">Delete</a></td>
							</tr>
					${word.word}
				</c:forEach>
					</table>
				</c:if>
			</div>
		</div>
	</div>

	<footer class="container-fluid">
		<p>Footer Text</p>
	</footer>

</body>
</html>
