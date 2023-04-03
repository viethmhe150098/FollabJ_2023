// Provide global regexs to validate forms 

export const LENGTH30 =/^[\s]*\S[\S\s]{0,28}\S[\s]*$///for titles 
export const LENGTH50 =  /^[\S\s]{0,50}$/   //cho các input còn lại
export const LENGTH100 = /^[\S\s]{0,100}$/ //for description
export const PASSWORD_REGEX = /^(?=.*\d)[a-zA-Z0-9]{8,}$/
