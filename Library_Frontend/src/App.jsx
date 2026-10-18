import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'
import Dashboard from './pages/Dashboard/Dashboard'
import { Route, Router, Routes } from 'react-router'
import UserLayout from './pages/UserLayout/UserLayout'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Routes>
        {/* User routes */}
        <Route element = {<UserLayout/>}>
          <Route path="/" element={<Dashboard/>}/>
        </Route>
      </Routes>
    </>
  )
}

export default App
