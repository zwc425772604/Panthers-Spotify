<%--
  Document   : browse
  Created on : Oct 19, 2017, 8:55:35 PM
  Author     : Weichao ZHao
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp"
	value="${pageContext.request.servletContext.contextPath}"
	scope="request" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<link rel="stylesheet"
	href="https://www.w3schools.com/lib/w3-theme-dark-grey.css">
<script src="${cp}/resources/js/queue.js"></script>
<div>
	<h1>Play Queue</h1>
</div>
<nav class="navbar navbar-expand-md bg-dark navbar-dark"
	id="bootstrap-overrides-navbar">
	<ul class="navbar-nav mr-auto tab" id="navbar-ul">
		<li class="nav-item"><a class="nav-link tablinks"
			href="javascript:displayContent('queueDiv')">QUEUE </a></li>
		<li class="nav-item"><a class="nav-link tablinks"
			href="javascript:displayContent('historyDiv')">HISTORY</a></li>
	</ul>
</nav>

<div id="queueDiv" class="w3-container info-table">
	<h3>Now Playing</h3>
	<table class="w3-table-all w3-hoverable" id="now-playing-table">
		<thead>
			<tr class="w3-light-grey">
				<th></th>
				<!-- play/pause button -->
				<th></th>
				<!-- save to library -->
				<th>TITLE</th>
				<th>ARTIST</th>
				<th>ALBUM</th>
				<th></th>
				<!-- more -->
				<th></th>
				<!-- song duration -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><button class="unstyle-buttons playbar-play-button"
						data-toggle="tooltip-play" title="Play">
						<i class="material-icons"><span
							class="song-page-play-pause-button">play_circle_filled</span></i>
					</button></td>
				<td><button class="unstyle-buttons tracklist-save-button"
						data-toggle="tooltip-save" title="Remove from Your Library"
						onclick="removeSong()">
						<i class="material-icons"><span
							class="tracklist-add-delete-button">done</span></i>
					</button></td>
				<td>Song Title</td>
				<td>Artist Name</td>
				<td>Album</td>
				<td>
					<button class="w3-button more_button">
						<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
					</button>
				</td>
				<td>2:15</td>
			</tr>
		</tbody>
	</table>
	<br>
	<h3>Next Up</h3>
	<table class="w3-table-all w3-hoverable" id="next-playing-table">
		<thead>
			<tr class="w3-light-grey">
				<th></th>
				<!-- play/pause button -->
				<th></th>
				<!-- save to library -->
				<th>TITLE</th>
				<th>ARTIST</th>
				<th>ALBUM</th>
				<th></th>
				<!-- more -->
				<th></th>
				<!-- song duration -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><button class="unstyle-buttons playbar-play-button"
						data-toggle="tooltip-play" title="Play">
						<i class="material-icons"><span
							class="song-page-play-pause-button">play_circle_filled</span></i>
					</button></td>
				<td><button class="unstyle-buttons tracklist-save-button"
						data-toggle="tooltip-save" title="Remove from Your Library"
						onclick="removeSong()">
						<i class="material-icons"><span
							class="tracklist-add-delete-button">done</span></i>
					</button></td>
				<td>Song Title</td>
				<td>Artist Name</td>
				<td>Album</td>
				<td>
					<button class="w3-button more_button">
						<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
					</button>
				</td>
				<td>2:15</td>
			</tr>
		</tbody>
	</table>
</div>

<div id="historyDiv" class="w3-container info-table"
	style="display: none">
	<table class="w3-table-all w3-hoverable" id="next-playing-table">
		<thead>
			<tr class="w3-light-grey">
				<th></th>
				<!-- play/pause button -->
				<th></th>
				<!-- save to library -->
				<th>TITLE</th>
				<th>ARTIST</th>
				<th>ALBUM</th>
				<th></th>
				<!-- more -->
				<th></th>
				<!-- song duration -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><button class="unstyle-buttons playbar-play-button"
						data-toggle="tooltip-play" title="Play">
						<i class="material-icons"><span
							class="song-page-play-pause-button">play_circle_filled</span></i>
					</button></td>
				<td><button class="unstyle-buttons tracklist-save-button"
						data-toggle="tooltip-save" title="Remove from Your Library"
						onclick="removeSong()">
						<i class="material-icons"><span
							class="tracklist-add-delete-button">done</span></i>
					</button></td>
				<td>Song Title</td>
				<td>Artist Name</td>
				<td>Album</td>
				<td>
					<button class="w3-button more_button">
						<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
					</button>
				</td>
				<td>2:15</td>
			</tr>
		</tbody>
	</table>
</div>


<script>
	function displayContent(table) {
		var i;
		var x = document.getElementsByClassName("info-table");
		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";
		}
		document.getElementById(table).style.display = "block";
	}
</script>
