import './App.css';
import React from 'react'
import { useState } from 'react';
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import { getData, uploadData } from './FetchData';
import { trackPromise } from 'react-promise-tracker';
const App = () => {
  const BASE_URL = 'http://localhost:8080'

  const GET_COUNTRY_DATA_ENDPOINT = BASE_URL + "/rest/data";

  const UPLOAD_COUNTRY_DATA_ENDPOINT =  BASE_URL + "/rest/data/upload";

  const [input, setInput] = useState({
    country: "Bulgaria",
    city: null,
    date: null
  });
  const [countryData, setCountryData] = useState(null);


  const handleSubmit = (event) => {
    event.preventDefault();

   let endpoint = GET_COUNTRY_DATA_ENDPOINT + '?country='+ input.country + "&city="+ input.city +"&date="+ input.date;
   
   trackPromise(
    getData('GET',endpoint).then(response =>{
      setCountryData(JSON.stringify(response, null, 2));
      Alert.success('Success!')
     })
     .catch(error =>{
      Alert.error("Something went wrong!");
     })
     )
  }

  const handleUpload =(event) =>{
    event.preventDefault();
    trackPromise(
      uploadData('POST',UPLOAD_COUNTRY_DATA_ENDPOINT,countryData).then(response =>{
        setCountryData(null);
        Alert.success('Success!')
      })
      .catch(error => {
        Alert.error("Something went wrong!");
      })
    );
  } 
  const handleChange = (e) => {
    const name = e.target.name;

    const value = e.target.value;

    setInput(values => ({ ...values, [name]: value }))
  }


  return (
    <div className="destination-container">
      <div className="destination-content">

        <h1 className="title">Check Destination</h1>

        <form onSubmit={handleSubmit}>
          <div className="form-item">
            <label for="country">Choose Country:</label>
            <br />
            <select className="form-control" name="country" id="country" value={input.country} onChange={handleChange}>
              
                <option value="Norway">Norway</option>
                <option value="Italy">Italy</option>
                <option value="Bulgaria">Bulgaria</option>
                <option value="Germany">Germany</option>
              
            </select>
          </div>
          <div className="form-item">
            <input type="text" name="city"
              className="form-control" placeholder="City name"
              value={input.city || ""} onChange={handleChange} required />
          </div>
          <div className="form-item">
            <label for="start">Date: </label>

            <input type="date" className="form-control" id="start" name="date"
              value={input.date || ""} 
              
              onChange={handleChange} required />
          </div>
          <div className="form-item">
            <button type="submit" onSubmit={handleSubmit} className="btn btn-block btn-primary" style={{ fontSize: '20px' }}>Submit</button>
          </div>
        </form>

        {countryData != null ?
          <form  onSubmit = {handleUpload}>
          <div className="signup-container">
            <div className="form-item">
              <textarea style={{width:430, height:300}} value={countryData}></textarea>
            </div>
            <div className="form-item">
              <button type="submit"  onSubmit={handleUpload} className="btn btn-primary" style={{ fontSize:'15px', fontWeight:'bold' }}>Upload to FTP</button>

            </div>
          </div>
           </form>
          :
          <></>
        }
      </div>

      <Alert
      stack={{limit: 3}} 
          timeout = {3000}
          position='top-right' effect='slide' offset={65} />
    </div>
  )
}

export default App