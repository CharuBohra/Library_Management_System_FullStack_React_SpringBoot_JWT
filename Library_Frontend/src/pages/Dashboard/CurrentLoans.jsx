import React from 'react'
import CurrentLoanCard from './CurrentLoanCard'

const loan = {
    bookTitle : "Too good to be true",
    bookCoverImageUrl : "https://cdn.pixabay.com/photo/2019/02/15/11/04/book-3998252_1280.jpg",
    bookAuthor : "Prajakta Koli" , 
    status :"ACTIVATED",
    dueDate : "2026-10-01",
    remainingDays : 10,
    overDueDays : 5,

}

const CurrentLoans = () => {
  return (
    <div className='p-6'>
        <h3 className='text-2xl font-bold text-gray-900 mb-2'>
            Books you're currently reading.
        </h3>
        <div>
            {/* List of current loans */}
            {[1,1,1,1].map((item,index)=><CurrentLoanCard key={index} loan={loan}/>)}
        </div>
    </div>
  )
}

export default CurrentLoans