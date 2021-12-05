export function getData(methodType, url) {  
        return fetch(url, {
          method: methodType,
          headers: {
              'Content-Type': 'application/json',
          },
         
      }).then(response =>{
          if(!response.ok){
              return Promise.reject(response);
          }else{
              return response.json();
          }
      });
        
}

export function uploadData(methodType, url, body) {  
    return fetch(url, {
      method: methodType,
      headers: {
          'Content-Type': 'application/json',
      },
     body: body
  }).then(response =>{
      if(!response.ok){
          return Promise.reject(response);
      }else{
          return response;
      }
  });
    
}

