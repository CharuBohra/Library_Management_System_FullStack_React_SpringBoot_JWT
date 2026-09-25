import React from 'react'

const Dashboard = () => {
  return (
    <div className='min-h-screen bg-gradient-to-br from-indigo-100 via-white to-purple-600 py-8'>
        <div className='max-w-7xl px-4 sm:px-6 lg:px-8'>
            <div className='mb-8 animate-fade-in-up'>
                <h1 className='text-4xl font-bold text-indigo-400 mb-2'>My {" "}
                    <span className='bg-gradient-to-br from-indigo-600 to-purple-600 
                    bg-clip-text text-transparent'>Dashboard</span>
                </h1>
                <p className='text-lg text-gray-700'>
                    Track your reading journey and manage your Library
                </p>
            </div>
        </div>
    </div>
  )
}

export default Dashboard