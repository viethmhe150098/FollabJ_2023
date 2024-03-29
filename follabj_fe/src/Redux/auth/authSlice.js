import { createSlice } from "@reduxjs/toolkit";


const authSlice = createSlice({
    name:"auth",
    initialState:{
        login:{
            currentUser:null,
            isFetching: false,
            expirationDate: null,
            error:false
        },
        register:{
            isFetching: false,
            error: false,
            success: false
        },
        
    },
    reducers:{
        loginStart: (state) =>{
            state.login.isFetching=true;
            state.login.error=false;
        },
        loginSuccess: (state,action) =>{
            state.login.isFetching=false;
            state.login.currentUser = action.payload;
            state.login.error=false;
            state.login.expirationDate = new Date().getTime() + 10*60*1000
        },
        loginFailed: (state) =>{
            state.login.isFetching=false;
            state.login.error = true;
        },

         registerStart: (state) =>{
             state.register.isFetching=true;
         },
         registerSuccess: (state) =>{
             state.register.isFetching=false;
             state.login.error=false;
             state.register.success=true;
         },
         registerFailed: (state) =>{
             state.register.isFetching=false;
             state.register.error = true;
             state.register.success=false;
         },

        
    }
});

export const {
    loginStart,
    loginFailed,
    loginSuccess,

    registerStart,
    registerFailed,
    registerSuccess,

    
} = authSlice.actions;


export default authSlice.reducer;