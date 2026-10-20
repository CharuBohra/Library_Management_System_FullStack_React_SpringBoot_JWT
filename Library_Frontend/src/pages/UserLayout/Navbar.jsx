import { AppBar, IconButton, Toolbar, Tooltip, Typography,Box, Avatar, Menu } from '@mui/material'
import { Menu as MenuIcon } from '@mui/icons-material'
import React from 'react'
import { navigationItems } from './NavigationItems'
import { isActive } from './utils'
import { Contrast, Notifications, Search } from '@mui/icons-material'
import { useLocation } from 'react-router'

const drawerWidth = 280
const user = {
    fullName: "John Doe",
    profile:"https://media.istockphoto.com/id/2199471016/photo/close-up-hands-of-unrecognizable-man-holding-and-using-smartphone-standing-on-city-street.jpg?s=1024x1024&w=is&k=20&c=krRxRFK-DldtAvf9BoPiQJYhP1RSYDtvFUMO7ujg1wg="

};
const Navbar = ({handleDrawerToggle}) => {

    const location = useLocation();
  return (
    <AppBar position='fixed' sx={{
        width : {md : `calc(100% - ${drawerWidth}px)`},
        ml : {md:`${drawerWidth}px`},
        bgcolor:"white",
        color:"text.primary",
        boxShadow:"0 1px 3px rgba(0,0,0,0.08)"
    }}>
        <Toolbar>
            <IconButton
                color='inherit'
                edge="start"
                onClick={handleDrawerToggle}
                sx={{
                    mr:2,
                    display:{md:"none"}
               }}>
                <Menu/>
            </IconButton>

            <Typography
                variant='h6'
                noWrap
                component="div"
                sx={{
                    flexGrow:1,
                    fontWeight:600
                }}
            >
                {navigationItems.find((item)=>
                        isActive(item.path,location))?.title||"Dashboard"}
            </Typography>

            <Tooltip title="Search">
                <Search/>
            </Tooltip>

            <Notifications sx={{ml:2}}/>

            <Box sx={{ ml:2}}>
                <Contrast/>
            </Box>

            <Tooltip title="Account">
                <IconButton 
                    //  onClick={handleProfileMenuDropdown}
                     sx={{ml:1}}
                >
                    <Avatar
                        src={user?.profile}
                        sx={{ width : 36,height:36}}
                    >
                        {user?.fullName?.charAt(0)}
                    </Avatar>
                </IconButton>
            </Tooltip>
        </Toolbar>
    </AppBar>
  )
}

export default Navbar