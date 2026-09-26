import { EventAvailable, History, LibraryBooks, TrendingUp } from '@mui/icons-material'
import React from 'react'

export const statsConfig = ({myLoans,reservations,stats}) => [
    {
        id:"loans",
        title:"Current Loans",
        subtitle:"Books you are reading",
        value:myLoans.length,
        icon:<LibraryBooks sx={{ fontSize:32 , color:"#4F46E5"}}/>,
        bgColor:"bg-indigo-100",
        textColor:"text-indigo-600"
    },
    {
        id:"reservations",
        title:"Reservations",
        subtitle:"Books on hold",
        value:reservations?.length || 0,
        icon:<EventAvailable sx={{ fontSize:32 , color :"#9333EA" }}/>,
        bgColor:"bg-purple-100",
        textColor:"text-purple-600"
    },
    {
        id:"read",
        title:"Books Read",
        subtitle:"This year",
        value:myLoans.length,
        icon:<History sx={{fontSize:32 , color:"#10B981"}}/>,
        bgColor:"bg-green-100",
        textColor:"text-green-600"
    },{
        id:"streak",
        title:"Day Streak",
        subtitle:"Keep it going!!",
        value:stats.readingStreak,
        icon:<TrendingUp sx={{fontSize:32 , color:"#F59E0B"}}/>,
        bgColor:"bg-orange-100",
        textColor:"text-orange-600"
    }
]