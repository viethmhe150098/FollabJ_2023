function isLoggedIn() {
    const access_token = localStorage.getItem('access_token');
    return access_token !== null;
  }
  
  export { isLoggedIn };