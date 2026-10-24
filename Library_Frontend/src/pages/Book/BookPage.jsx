import React from 'react'
import GenreFilter from './GenreFilter'
import { useState } from 'react';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';

// Mirrors GenreDTO from the Spring Boot backend.
// LocalDateTime serialises as an ISO-8601 string (Spring Boot default).
export const genres= [
  {
    id: 1,
    code: "FICTION",
    name: "Fiction",
    description: "Imaginative narratives including novels and short stories.",
    displayOrder: 1,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 120,
    createdAt: "2026-01-10T09:15:00",
    updatedAt: "2026-09-02T14:30:00",
    subGenres: [
      {
        id: 11,
        code: "FICTION_FANTASY",
        name: "Fantasy",
        description: "Magic, mythical creatures and imaginary worlds.",
        displayOrder: 1,
        active: true,
        parentGenreId: 1,
        parentGenreName: "Fiction",
        bookCount: 45,
        createdAt: "2026-01-12T10:00:00",
        updatedAt: "2026-08-20T11:45:00",
        subGenres: []
      },
      {
        id: 12,
        code: "FICTION_MYSTERY",
        name: "Mystery & Thriller",
        description: "Crime, suspense and detective stories.",
        displayOrder: 2,
        active: true,
        parentGenreId: 1,
        parentGenreName: "Fiction",
        bookCount: 38,
        createdAt: "2026-01-12T10:05:00",
        updatedAt: "2026-07-14T16:20:00",
        subGenres: []
      },
      {
        id: 13,
        code: "FICTION_ROMANCE",
        name: "Romance",
        description: "Stories centred on relationships and love.",
        displayOrder: 3,
        active: true,
        parentGenreId: 1,
        parentGenreName: "Fiction",
        bookCount: 37,
        createdAt: "2026-01-12T10:10:00",
        updatedAt: "2026-06-30T09:00:00",
        subGenres: []
      }
    ]
  },
  {
    id: 2,
    code: "NON_FICTION",
    name: "Non-Fiction",
    description: "Factual writing: biographies, history, self-help and more.",
    displayOrder: 2,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 95,
    createdAt: "2026-01-10T09:20:00",
    updatedAt: "2026-09-05T12:10:00",
    subGenres: [
      {
        id: 21,
        code: "NON_FICTION_BIOGRAPHY",
        name: "Biography",
        description: "Life stories of notable people.",
        displayOrder: 1,
        active: true,
        parentGenreId: 2,
        parentGenreName: "Non-Fiction",
        bookCount: 30,
        createdAt: "2026-01-15T08:30:00",
        updatedAt: "2026-08-01T10:00:00",
        subGenres: []
      },
      {
        id: 22,
        code: "NON_FICTION_SELF_HELP",
        name: "Self-Help",
        description: "Personal growth, productivity and wellbeing.",
        displayOrder: 2,
        active: true,
        parentGenreId: 2,
        parentGenreName: "Non-Fiction",
        bookCount: 40,
        createdAt: "2026-01-15T08:35:00",
        updatedAt: "2026-09-10T15:25:00",
        subGenres: []
      }
    ]
  },
  {
    id: 3,
    code: "SCIENCE_TECH",
    name: "Science & Technology",
    description: "Physics, computing, engineering and popular science.",
    displayOrder: 3,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 64,
    createdAt: "2026-01-11T11:00:00",
    updatedAt: "2026-08-28T17:40:00",
    subGenres: [
      {
        id: 31,
        code: "SCIENCE_PROGRAMMING",
        name: "Programming",
        description: "Software engineering, languages and frameworks.",
        displayOrder: 1,
        active: true,
        parentGenreId: 3,
        parentGenreName: "Science & Technology",
        bookCount: 28,
        createdAt: "2026-01-18T13:00:00",
        updatedAt: "2026-09-12T08:15:00",
        subGenres: []
      }
    ]
  },
  {
    id: 4,
    code: "HISTORY",
    name: "History",
    description: "Events, civilisations and people of the past.",
    displayOrder: 4,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 33,
    createdAt: "2026-01-11T11:30:00",
    updatedAt: "2026-07-22T10:50:00",
    subGenres: []
  },
  {
    id: 5,
    code: "CHILDREN",
    name: "Children's Books",
    description: "Picture books and early readers for young audiences.",
    displayOrder: 5,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 52,
    createdAt: "2026-02-01T09:00:00",
    updatedAt: "2026-09-18T13:35:00",
    subGenres: []
  },
  {
    id: 6,
    code: "POETRY",
    name: "Poetry",
    description: "Collections of poems and verse.",
    displayOrder: 6,
    active: false,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 0,
    createdAt: "2026-03-05T14:00:00",
    updatedAt: "2026-10-01T09:30:00",
    subGenres: []
  }
];
const BookPage = () => {

    const [selectedGenreId , setSelectedGenreId] = useState(null);
    const [availability, setAvailability] = useState('ALL')

    const handleGenreChange = (genreId) =>{
        setSelectedGenreId(genreId);
    }
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
                        <GenreFilter genres={genres} onGenreSelect={handleGenreChange} selectedGenreId={selectedGenreId}/>
                        {/* Availability Filter  */}
                        <div className='bg-white rounded-xl shadow-md p-4 border border-gray-100'>
                            <h3 className='text-lg font-bold text-gray-900 mb-4 pb-3 border-b border-gray-200'>
                                Availability
                            </h3>
                            <FormControl fullWidth>
                                <InputLabel id="demo-simple-select-label">Availability</InputLabel>
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={availability}
                                    label="Availability"
                                    inputProps={{ 'aria-label': 'Availability' }}
                                    onChange={(e)=>setAvailability(e.target.value)}
                                    sx={{
                                        fontSize: '0.875rem',     // 14px, same as text-sm
                                        fontFamily: 'inherit',    // use the page font instead of Roboto
                                        color: '#374151',         // text-gray-700
                                    }}
                                    MenuProps={{
                                        slotProps:{
                                            paper:{
                                                sx:{
                                                    '&. MuiMenuItem-root':{
                                                        fontSize: '0.875rem',     // 14px, same as text-sm
                                                        fontFamily: 'inherit',
                                                        color: '#374151',
                                                    }
                                                }
                                            }
                                        }
                                    }}
                                >
                                    <MenuItem value={'ALL'}>All Books</MenuItem>
                                    <MenuItem value={'AVAILABLE'}>Available Books</MenuItem>
                                    <MenuItem value={'CHECKED_OUT'}>Checked Out</MenuItem>
                                </Select>
                            </FormControl>
                        </div>
                    </div>
                    
                </aside>
            </div>
        </div>
    </div>
  )
}

export default BookPage