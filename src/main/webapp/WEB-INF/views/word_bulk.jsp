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

	function showURL() {
		$("#urlDiv").show();
		$("#textDiv").hide();
		$("#text").val("");
	}

	function showText() {
		$("#textDiv").show();
		$("#urlDiv").hide();
		$("#url").val("");
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav">
				<h2>Vocabulary Easier</h2>
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="/">Home</a></li>
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

				<c:url var="addAction" value="/add_bulk"></c:url>

				<form:form action="${addAction}" commandName="wordBulk" id="form"
					acceptCharset="UTF-8">
					<div class="form-group">
						<form:label path="bulkTypes">
							<spring:message text="Bulk types" />
						</form:label>
						<form:radiobutton path="selectedBulkType" value="url" label="URL"
							onclick="showURL()" />
						<form:radiobutton path="selectedBulkType" value="text"
							label="Text" onclick="showText()" />
					</div>
					<div class="form-group" id="urlDiv"
						style="display:${wordBulk.selectedBulkType == 'text' ? 'none' : 'block'}">
						<form:label path="url">
							<spring:message text="URL" />
						</form:label>
						<form:input path="url" size="100" id="url" />
					</div>
					<div class="form-group" id="textDiv"
						style="display:${wordBulk.selectedBulkType == 'text' ? 'block' : 'none'}">
						<form:label path="url">
							<spring:message text="Text" />
						</form:label>
						<form:textarea path="text" rows="10" cols="100" id="text" />
					</div>
					<div class="form-group" id="topic">
						<form:label path="topic">
							<spring:message text="Topic" />
						</form:label>
						<form:input path="topic" />
					</div>
					<div class="form-group">
						<input type="button" value="<spring:message text="Submit"/>"
							class="btn btn-primary" />
					</div>

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
								<td>
									<%-- <img src="${word.image}" height="100px" width="100px" /> --%>
								</td>
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
