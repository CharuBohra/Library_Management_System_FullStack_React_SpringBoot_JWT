import React from 'react'
import StateCard from './StateCard'
import { LibraryBooks } from '@mui/icons-material'
import { statsConfig } from './StateConfig'

const Dashboard = () => {

  const stateData = statsConfig({
    myLoans:[1,2,3,4,5],
    reservations:[6,7,8],
    stats:{readingStreak:5}
  })
  return (
    <div className='min-h-screen bg-linear-to-br from-indigo-100 via-white to-purple-600 py-8'>
        <div className='max-w-7xl px-4 sm:px-6 lg:px-8'>
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

        </div>
    </div>
  )
}

export default Dashboard