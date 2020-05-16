using AutoMapper;
using FlowApp.API.Helper;
using FlowApp.Business.Abstract;
using FlowApp.Core.DTOs;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Net;
using System.Security.Claims;

namespace App.API.Controllers
{
    [Produces("application/json")]
    [Route("api/posts")]
    [Authorize]
    [ApiController]

    public class PostController : Controller
    {
        private readonly IPostService _postService;
        private readonly IUserService _userService;
        private readonly IMapper _mapper;

        public PostController(IPostService postService, IUserService userService, IMapper mapper)
        {
            _postService = postService;
            _userService = userService;
            _mapper = mapper;
        }

        [HttpGet]
        public IActionResult GetPosts()
        {
            var posts = _postService.GetPostAll();
            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: posts);
        }

        [HttpGet("{page}")]
        public IActionResult GetPosts(int page)
        {
            var posts = _postService.GetPostPageAll(page, 10);
            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: posts);
        }

        [HttpGet("post/{postId}")]
        public IActionResult GetPost(int postId)
        {
            var post = _postService.GetPost(postId);
            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: post);
        }


        [HttpPost()]
        public IActionResult Post(PostDto postDto)
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);
            var post = _postService.Save(userId, postDto);

            if (post != null)
            {
                return new ObjectActionResult(success: true, statusCode: HttpStatusCode.Created, data: post);
            }

            return BadRequest();
        }
    }
}