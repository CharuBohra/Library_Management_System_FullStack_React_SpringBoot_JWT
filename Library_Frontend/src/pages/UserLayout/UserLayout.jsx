
import { Box, Toolbar } from '@mui/material'
import React from 'react'
import UserSidebar from './UserSidebar'
import { Outlet } from 'react-router'

const drawerWidth = 240
const UserLayout = () => {
  return (
    <Box sx={{display:"flex" , minHeight:"100vh" , bgcolor : "white"}}>
        {/* App bar */}

        {/* Profile Menu  */}

        {/* User sidebar */}
        <UserSidebar/>

        {/* Main content */}
        <Box component={"main"} sx={{
            flexGrow : 1,
            width : {md:`calc(100% - ${drawerWidth}px)`},
            minHeight:'100vh',
            padding : 2
        }}>
            <Toolbar/>
            <Box>
                <Outlet/>
            </Box>
        </Box>
    </Box>
  )
}

export default UserLayout