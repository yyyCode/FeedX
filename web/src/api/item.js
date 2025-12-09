import http from './http';

export const uploadItem = (formData) =>
  http.post('/item', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });

export const fetchItem = (id) => http.get(`/item/${id}`);

export const listItems = (params = {}) => http.get('/item/list', { params });

