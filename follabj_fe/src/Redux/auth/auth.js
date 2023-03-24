function isLoggedIn() {

    var isLoggedIn = false
    
    const currentDate = new Date()

    const access_token = localStorage.getItem('access_token');
    if (access_token) {
       const expire_date = localStorage.getItem('expire_date')
      //  console.log("current date "+ currentDate.getTime())
      //  console.log("expire date "+ expire_date)
       if (currentDate.getTime() < expire_date) {
         isLoggedIn = true
       } else {
         localStorage.clear()
       }
    }
    

    return isLoggedIn
  }
  
  export { isLoggedIn };