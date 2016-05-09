<s:form action="surveyreadingsupdate_action" method="POST">
							<input type="checkbox" name="validationRequest"
								value="validateRequest" checked="checked" style="display: none;" />


							<tr>

								<s:actionerror />
								<s:hidden name="surveyReading.readingId"
									value="%{#request.readingId}" />
									<s:hidden name="surveyName"
									value="%{#session.survey.surveyName}" />
								<s:label>Default calculation is for Dynamic pressure where Pd = 1/2pv2</s:label>
								<td colspan="2"><s:textfield label="Location"
										name="surveyReading.location" size="40" value="%{#session.surveyReading.location}" required="true" /></td>
								<td colspan="2"><s:textfield label="Description"
										name="surveyReading.description" size="40"  value="%{#session.surveyReading.description}"/></td>
								<td colspan="2"><s:textfield label="Density (kg/m3) "
										name="surveyReading.density" size="15" value="%{#session.surveyReading.density}" required="true" /></td>
								<td colspan="2"><s:textfield label="Velocity (m/s)"
										name="surveyReading.velocity" size="15" value="%{#session.surveyReading.velocity}" required="true" /></td>
								<td colspan="2"><s:select name="surveyReading.readingType"
										label="Reading Type " headerKey=" "
										headerValue="Dynamic Pressure" list="readingTypes"
										listKey="description" listValue="description" value="%{#session.surveyReading.readingType}" required="true" /></td>
								<td colspan="2"><s:select name="surveyReading.unit"
										label="Unit" headerKey=" " headerValue="Pd" list="units"
										listKey="description" listValue="description" value="%{#session.surveyReading.unit}" required="true" /></td>
								<td align="right" colspan="2"><input type="reset"
									value="Reset" /><input type="submit" value="Submit" /></td>
							</tr>


						</s:form>