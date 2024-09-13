import axios from 'axios';
import { useEffect, useState } from 'react';

const DisplayImage = () => {
  const [imageSrc, setImageSrc] = useState<string | null>(null);

  const fetchImage = async () => {
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_API_URL}/images/1`,
        {
          responseType: 'blob', // Ensure we get the image as a Blob
        }
      );

      // Create a URL for the image blob
      const imageBlob = new Blob([response.data], {
        type: response.headers['content-type'],
      });
      const imageUrl = URL.createObjectURL(imageBlob);

      setImageSrc(imageUrl);
    } catch (error) {
      console.error('Error fetching the image:', error);
    }
  };

  useEffect(() => {
    // Cleanup the URL object when component unmounts
    return () => {
      if (imageSrc) {
        URL.revokeObjectURL(imageSrc);
      }
    };
  }, [imageSrc]);

  return (
    <div>
      <h1>Image Display</h1>
      {imageSrc && (
        <img
          src={imageSrc}
          alt='Uploaded'
          style={{ width: '300px', height: 'auto' }}
        />
      )}
      <button onClick={fetchImage}>Get Image</button>
    </div>
  );
};

export default DisplayImage;
