import { Dashboard, MenuBook } from '@mui/icons-material'
import { Avatar, Box, List, ListItem, ListItemButton, ListItemIcon, Tooltip, Typography } from '@mui/material'
import React from 'react'

const navigaionItems = [{
    'description':'User Dashboard',
    'title':"Dashboard",
    'icon':<Dashboard/>
}]
const SidebarDrawer = () => {
  return (
    <Box sx={{
        height:"100%",
        display:"flex",
        flexDirection:"column",
        background: "linear-gradient(180deg, #1e293b 0%, #0f172a 100%)",
        color:"white",
        position:"relative",
        overflow:"hidden",
        "&::before":{
            content:'""',
            position:"absolute",
            top:0,
            left:0,
            right:0,
            height:"300px",
            background:"radial-gradient(circle at 50% 0% , rgba(99,102,241,0.15) 0% , transparent 100%)",
            pointerEvents:"none"
        }
    }}>
        <Box>
            <Box sx={{
                p:3,
                display:"flex",
                alignItems:"center",
                gap:2,
                position:"relative",
                zIndex:1
            }}>
                <Avatar sx={{
                    width:48,
                    height:48,
                    background: "linear-gradient(135deg , #667eea 0% , #764ba2 100%)",
                    fontWeight:"bold",
                    fontSize:"1.3rem",
                    boxShadow: "0 8px 24px rgba(102,126,234,0.4)"
                }}>
                    <MenuBook/>
                </Avatar>
                <Box>
                    <Typography 
                        variant='h6'
                        sx={{
                            fontWeight:800,
                            letterSpacing:0.5,
                            background:"linear-gradient(135deg , #ffffff 0% , #e0e7ff 100%)",
                            backgroundClip:"text",
                            WebkitBackgroundClip:"text"
                        }}
                    >
                        ZoshBook
                    </Typography>
                    <Typography
                        variant='caption'
                        sx={{
                            opacity:0.7,
                            fontWeight:500,
                            letterSpacing:1,
                            textTransform:"uppercase"
                        }}
                    >
                        Library Hub
                    </Typography>
                </Box>
            </Box>
        </Box>

        <Box>
            <List>
                {navigaionItems.map((item,index)=>{
                    const active = true;

                    return <ListItem key={index}>
                        <Tooltip title={item.description} placement='right'>
                            <ListItemButton
                                sx={{
                                    borderRadius:2.5,
                                    py:1.5,
                                    px:2,
                                    transition:'all 0.3s cubic-bezier(0.4,0,0.2,1)',
                                    position:'relative',
                                    backgroundColor: active? 'linear-gradient(135deg , rgba(99,102,241,0.25) 0% , rgba(168,85,247,0.25) 100%)': 'transparent',
                                    border:active? '1px solid rgba(99,102,241,0.3)':'1px solid transparent',
                                    backdropFilter : active? 'blur(10px)':none;
                                    
                                }}
                            >
                                <ListItemIcon>{item.icon}</ListItemIcon>
                                {item.title}
                            </ListItemButton>
                        </Tooltip>
                    </ListItem>
                })}
            </List>
        </Box>
    </Box>
    
  )
}

export default SidebarDrawer