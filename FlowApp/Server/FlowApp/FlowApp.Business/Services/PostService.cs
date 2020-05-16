using System.Collections.Generic;
using System.Diagnostics;
using System.Threading.Tasks;
using FlowApp.Business.Abstract;
using FlowApp.Core.DTOs;
using FlowApp.Core.Util.Azure;
using FlowApp.Data.Abstract;
using FlowApp.Entity.Models;

namespace FlowApp.Business.Services
{
    public class PostService : IPostService
    {
        public readonly IPostRepository _postRepository;
        public readonly ITextRepository _textRepository;
        public readonly IPictureRepository _pictureRepository;
        public readonly IAzureStorageService _azureStorageService;

        public PostService(IPostRepository postRepository, ITextRepository textRepository, IPictureRepository pictureRepository, IAzureStorageService azureStorageService)
        {
            _postRepository = postRepository;
            _textRepository = textRepository;
            _pictureRepository = pictureRepository;
            _azureStorageService = azureStorageService;
        }

        public Task<bool> Any(int postId)
        {
            return _postRepository.Any(x => x.Id == postId);
        }

        public ViewPost GetPost(int postId)
        {
            return _postRepository.GetPostList(postId);
        }

        public List<ViewPost> GetPostAll()
        {
            return _postRepository.GetPostsList();
        }

        public List<ViewPost> GetPostPageAll(int page, int pageSize)
        {
            return _postRepository.GetPostsList(page,pageSize);
        }

        public bool Remove(int postId)
        {
            throw new System.NotImplementedException();
        }

        public ViewPost Save(int userId, PostDto postDto)
        {
            var post = new Post()
            {
                UserId = userId
            };

            post = _postRepository.Add(post);

            var text = new Text()
            {
                PostId = post.Id,
                UserId = userId,
                Description = postDto.Description
            };

            _textRepository.Add(text);

            var picture = new Picture()
            {
                PostId = post.Id,
                UserId = userId
            };

            if (postDto.Images != null)
            {
                postDto.Images.ForEach(async delegate (string img)
                {
                    var path = await _azureStorageService.UploadFileToBlobAsync("img", img, "image/jpeg");
                    Debug.WriteLine("Resim:" + path);
                    picture.Img = path;
                    _pictureRepository.Add(picture);
                });
            }

            return GetPost(post.Id);
        }
    }
}