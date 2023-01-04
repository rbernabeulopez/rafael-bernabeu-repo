import {BrowserRouter, Route, Routes} from "react-router-dom";
import Nav from "./components/layout/Nav";
import Home from "./components/views/Home";
import Secured from "./components/views/Secured";

function App() {
    return (
        <>
            <BrowserRouter>
                <Nav/>
                <Routes>
                    <Route exact path="/" element={<Home/>}/>
                    <Route path="/secured" element={<Secured/>}/>
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
