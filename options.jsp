<html><%/*
<head>
	<link rel="stylesheet" type="text/css" href="layout.css">
	<link rel="stylesheet" type="text/css" href="reg_log.css">
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css"> 

</head>*/%>
<body><%/*
<div style="background-color:#333333">
<div class="container">*/%>
	<table class="table table-bordered table-condensed" style="width:100%">
		<thead>
		<tr>
			<th class="text-center"><u>Tweet Table</u></th>
			<th class="text-center"><u>User Table</u></th>
			<th class="text-center"><u>Place Table</u></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>Tweet ID *</td>
			<td>User ID *</td>
			<td>Place ID *</td>

		</tr>
		<tr>
			<td>User ID *</td>
			<td>Screenname <input type="checkbox" name="sn_ur" id="sn_ur"/></td>
			<td>Bounding Region <input type="checkbox" name="br_pl" id="br_pl"/></td>

		</tr>
		<tr>
			<td>Place ID *</td>
			<td>Date created <input type="checkbox" name="dt_ur" id="dt_ur"/></td>
			<td>Fullname <input type="checkbox" name="fn_pl" id="fn_pl"/></td>

		</tr>
		<tr>
			<td>Date created <input type="checkbox" name="dt_tw" id="dt_tw"/></td>
			<td>Utcoffset <input type="checkbox" name="utc_ur" id="utc_ur"/></td>
			<td>Country <input type="checkbox" name="c_pl" id="c_pl"/></td>
	
		</tr>
		<tr>
			<td>Text <input type="checkbox" name="t_tw" id="t_tw"/></td>
			<td>Description <input type="checkbox" name="ds_ur" id="ds_ur"/></td>
			<td>CountryCode <input type="checkbox" name="cc_pl" id="cc_pl"/></td>
	
		</tr>
		<tr>
			<td>Lang <input type="checkbox" name="l_tw" id="l_tw"/></td>
			<td>Location <input type="checkbox" name="lc_ur" id="lc_ur"/></td>
			<td>PlaceType <input type="checkbox" name="pt_pl" id="pt_pl"/></td>
		</tr>
		<tr>
			<td>InReplyToUserId <input type="checkbox" name="rtu_tw" id="rtu_tw"/></td>
			<td>StatusesCount <input type="checkbox" name="sc_ur" id="sc_ur"/></td>
			<td>StreetAddress <input type="checkbox" name="sa_pl" id="sa_pl"/></td>
		</tr>
		<tr>
			<td>InReplyToStatusId <input type="checkbox" name="rts_tw" id="rts_tw"/></td>                        
			<td>FavouritesCount <input type="checkbox" name="fv_ur" id="fv_ur"/></td>
			<td></td>
		</tr>
		<tr>
			<td>GeoLocation <input type="checkbox" name="gl_tw" id="gl_tw"/></td>
			<td>Followerscount <input type="checkbox" name="fw_ur" id="fw_ur"/></td>
			<td></td>
		</tr>
		<tr>
			<td>Source <input type="checkbox" name="s_tw" id="s_tw"/></td>
			<td>Friendscount <input type="checkbox" name="fr_ur" id="fr_ur"/></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td>Verified <input type="checkbox" name="v_ur" id="v_ur"/></td>
			<td></td>
		</tr>
		</tbody>
		</table>
		<table class="table table-bordered table-condensed" style="width:100%">
			<thead>
				<tr>
					<th style="border-right-style:none;"> </th>
					<th class="text-center" style="border-left-style:none;border-right-style:none;"><u>Additional Tables</u></th>
					<th style="border-left-style:none;"> </th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="text-center">MediaEntities <input type="checkbox" name="m_e" id="m_e" disabled></td>
					<td class="text-center">URLEntities <input type="checkbox" name="u_e" id="u_e" disabled></td>
					<td class="text-center">UserMentionEntities <input type="checkbox" name="um_e" id="um_e" disabled></td>
				</tr>
			</tbody>
		</table>

</body>
</html>
