import React from 'react'

const BookPage = () => {
  return (
    <div className='min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50'>
        {/* header */}
        <div className='bg-white border-b border-gray-200 text-center'>
            <div className='px-4 sm:px-6 lg:px-8 py-8'>
                <h1 className='text-4xl md:text-5xl font-bold mb-4 '>
                    Browse Our{" "}
                    <span className='bg-linear-to-br from-indigo-600 to-purple-600 bg-clip-text text-transparent'>
                        Collection
                    </span>
                </h1>
                <p className='text-lg text-gray-600'>
                    Discover thousands of books across all genres
                </p>
            </div>
        </div>
        {/* Main Content */}
        <div className='px-4 sm:px-6 lg:px-8 py-8'>
            <div className='flex flex-col lg:flex-row gap-8'>
                {/* Sidebar Filter */}
                <aside className='lg:w-72 space-y-6'>
                    {/* Filter Container */}
                    <div className='space-y-6'>
                        {/* <GenreFilter/> */}
                    </div>
                </aside>
            </div>
        </div>
    </div>
  )
}

export default BookPage