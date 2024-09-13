import { ChangeEvent, FormEvent, useState } from 'react';

import axios from 'axios';

const UploadImage = () => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [message, setMessage] = useState('');

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files ? event.target.files[0] : null;
    setSelectedFile(file);
  };

  const handleUpload = async (event: FormEvent) => {
    event.preventDefault();

    if (!selectedFile) {
      setMessage('Please select a file first.');
      return;
    }

    const formData = new FormData();
    formData.append('file', selectedFile); // 'file' matches @RequestParam("file") in Spring

    try {
      const response = await axios.post(
        `${import.meta.env.VITE_API_URL}/images/upload`,
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        }
      );
      setMessage(response.data);
    } catch (error) {
      console.error('Error uploading the image:', error);
      setMessage('Failed to upload the image.');
    }
  };

  return (
    <div>
      <h1>Upload an Image</h1>
      <form onSubmit={handleUpload}>
        <input type='file' accept='image/*' onChange={handleFileChange} />
        <button type='submit'>Upload</button>
      </form>
      <p>{message}</p>
    </div>
  );
};

export default UploadImage;
