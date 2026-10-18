import React from 'react'
import StateCard from './StateCard'
import { AutoAwesome, LibraryBooks } from '@mui/icons-material'
import { statsConfig } from './StateConfig'
import { LinearProgress } from '@mui/material'

const Dashboard = () => {

  const stateData = statsConfig({
    myLoans:[1,2,3,4,5,6,7,8],
    reservations:[6,7,8],
    stats:{readingStreak:5}
  })
  return (
    <div className='min-h-screen bg-linear-to-br from-indigo-100 via-white to-purple-600 py-8'>
        <div className='max-w-7xl px-4 sm:px-6 lg:px-8 mx-auto'>
            <div className='mb-8 animate-fade-in-up'>
                <h1 className='text-4xl font-bold text-indigo-400 mb-2'>My {" "}
                    <span className='bg-linear-to-br from-indigo-600 to-purple-600 
                    bg-clip-text text-transparent'>Dashboard</span>
                </h1>
                <p className='text-lg text-gray-700'>
                    Track your reading journey and manage your Library
                </p>
            </div>

            {/* {state card} */}

            <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8'>
                { stateData.map((item)=> <StateCard
                 bgColor= {item.bgColor}
                 textColor={item.textColor}
                 icon={item.icon}
                 value={item.value}
                 title={item.title}
                 subtitle={item.subtitle}
                 key={item.id}/>)}
            </div>

            {/* Progress card */}
            <div className='bg-white rounded-2xl shadow-2xl p-6 mb-8'>
                    <div className='flex items-center justify-between mb-4'>
                        <div >
                            <h3 className='text-xl font-bold text-gray-900 mb-1'>2026 Reading Goals</h3>
                            <p className='text-gray-600'>{250} of 30 books read.</p>
                        </div>
                        <div className='p-3 bg-gradient-to-br from bg-indigo-100 to-purple-100 rounded-full'>
                            <AutoAwesome sx={{
                                fontSize : 32,
                                color:"#4F46E5"
                            }}/>
                        </div>
                    </div>

                    <LinearProgress 
                        variant='determinate' 
                        value={30}
                        sx={{
                            height:12,
                            borderRadius:6,
                            backgroundColor:"#E0E7FF",
                            "& .MuiLinearProgress-bar" : {
                                backgroundColor:"linear-gradient(90deg ,#4F46E5 0% , #9333EA 100% )",
                                borderRadius:6
                            }
                        }}
                    />

                    <p className='text-sm text-gray-600 mt-2'>70%</p>
            </div>
        </div>
    </div>
  )
}

export default Dashboard